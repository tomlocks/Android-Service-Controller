package com.tomlocksapps.servicecontroller;

import android.os.Handler;

import com.tomlocksapps.servicecontroller.example.engine.CommunicationEngine;
import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceCommunicationEvent;
import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceLifecycleEvent;
import com.tomlocksapps.servicecontroller.example.engine.event.ServiceControllerEventCallback;
import com.tomlocksapps.servicecontroller.example.engine.event.ServiceEventWrapper;
import com.tomlocksapps.servicecontroller.manager.AbstractServiceManager;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by walczewski on 05.09.2016.
 */
public abstract class ServiceController implements ServiceControllerEventCallback {

    private final Handler handler = new Handler();
    private final ServiceEventWrapper serviceEventWrapper;

    private final Set<IServiceManager> services = new LinkedHashSet<>();

    private final Map<Class<? extends IServiceManager>, IServiceCommunication> onAppStartActionsMap = new HashMap<>();
    private final Set<Class<? extends IServiceManager>> allowedShortTimeServices = new HashSet<>(); // zawiera serwisy jednorazowe(stopSelf), które mogą wystartować dla tej implementacji ServiceController'a

    public ServiceController(CommunicationEngine engine) {
        this.serviceEventWrapper = engine.getServiceEventWrapper();
        serviceEventWrapper.setCallback(this);

        fillOnAppStartActionsMap(onAppStartActionsMap);
        fillShortTimeServiceSet(allowedShortTimeServices);

        allowedShortTimeServices.addAll(onAppStartActionsMap.keySet()); // dodanie wszystkich serisow jednorazowych
    }

    protected void fillShortTimeServiceSet(Set<Class<? extends IServiceManager>> allowedShortTime) {
    }

    ;

    protected void fillOnAppStartActionsMap(Map<Class<? extends IServiceManager>, IServiceCommunication> onAppStartActionsMap) {
    }

    public void onCreate() {
        serviceEventWrapper.initialize();



        for (IServiceManager service : services) {
            onCreateService(service);
        }

        executeOnAppStartActions();
    }

    protected void onCreateService(IServiceManager service) {
        service.setServiceControllerCallback(serviceManagerCallback);
        service.onCreate();
    }

    private void onStart(final IServiceCommunication serviceCommunication, final long delay) {
        for (IServiceManager service : services) {
            service.onStart(serviceCommunication, delay);
        }
    }

    public void onDestroy() {
        serviceEventWrapper.uninitialize();

        handler.removeCallbacksAndMessages(null);

        for (IServiceManager service : services) {
            service.onDestroy();
        }
    }

    public void onRestart() {
        for (IServiceManager service : services) {
            service.onRestart();
        }
    }

    public void onLowMemory() {
        for (IServiceManager service : services) {
            service.onLowMemory();
        }
    }

    @Override
    public void onNewServiceCommunicationEvent(final ServiceCommunicationEvent serviceCommunicationEvent) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                IServiceCommunication serviceCommunication = serviceCommunicationEvent.getServiceCommunication();
                if (serviceCommunication != null) {
                    onStart(serviceCommunication, serviceCommunicationEvent.getDelay());
                }
            }
        });
    }

    @Override
    public void onNewServiceLifecycleEvent(final ServiceLifecycleEvent serviceLifecycleEvent) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (serviceLifecycleEvent.getStartFlag()) { /* ---- START SERVICE ---- */
                    if (!allowedShortTimeServices.contains(serviceLifecycleEvent.getClazz()))
                        return;

                    boolean serviceNotYetCreated = true;
                    for (IServiceManager service : services) {
                        if (service.getClass().equals(serviceLifecycleEvent.getClazz())) {
                            onStart(serviceLifecycleEvent.getServiceCommunication(), serviceLifecycleEvent.getActionDelay());
                            serviceNotYetCreated = false;
                            break; //service jest już wystartowany
                        }
                    }

                    if (serviceNotYetCreated) {
                        Object[] args = serviceLifecycleEvent.getArgs();
                        Class[] argsClasses = new Class[args.length];
                        for (int i = 0; i < args.length; i++) {
                            argsClasses[i] = args[i].getClass();
                        }

                        IServiceManager serviceManager = null;
                        boolean withError = false;
                        try {
                            serviceManager = serviceLifecycleEvent
                                    .getClazz()
                                    .getConstructor(argsClasses)
                                    .newInstance(args);

                            onCreateService(serviceManager);

                            services.add(serviceManager);

                        } catch (InstantiationException e) {
                            e.printStackTrace();
                            withError = true;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            withError = true;
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            withError = true;
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                            withError = true;
                        }

                        if (withError) {
                            String parameters = "";
                            for (Class clazz : argsClasses) {
                                parameters += clazz.getSimpleName();
                                parameters += " ";
                            }
                            throw new IllegalStateException(serviceLifecycleEvent.getClazz().getName() + " nie posiada konstruktora z parametrami: " + parameters);
                        }
                    }

                    if (serviceLifecycleEvent.getServiceCommunication() != null)
                        onStart(serviceLifecycleEvent.getServiceCommunication(), serviceLifecycleEvent.getActionDelay());

                } else { /* ---- STOP SERVICE ---- */
                    IServiceManager serviceManager = null;
                    for (IServiceManager service : services) {
                        if (service.getClass().equals(serviceLifecycleEvent.getClazz())) {
                            serviceManager = service;
                            break;
                        }
                    }

                    if (serviceManager != null) {
                        serviceManager.onDestroy();
                        services.remove(serviceManager);
                    }
                }

            }
        });
    }

    private void executeOnAppStartActions() {
        for (Class<? extends IServiceManager> managerClass : onAppStartActionsMap.keySet()) {
            boolean hasService = false;

            for (IServiceManager service : services) {
                if(service.getClass().equals(managerClass))
                    hasService = true;
            }
            if(!hasService)
                throw new IllegalStateException(String.format("Did not find %s on services list. Please add it in constructor.", managerClass.getName()));


            onStart(onAppStartActionsMap.get(managerClass), 0);
        }
    }

    public void onTrimMemory(int level) {
        for (IServiceManager serviceManager : services) {
            serviceManager.onTrimMemory(level);
        }
    }

    protected void addService(IServiceManager iServiceManager) {
        services.add(iServiceManager);
    }


    private final AbstractServiceManager.ServiceManagerCallback serviceManagerCallback = new AbstractServiceManager.ServiceManagerCallback() {
        @Override
        public void stopSelf(Class<? extends AbstractServiceManager> aClazz) {
            onNewServiceLifecycleEvent(new ServiceLifecycleEvent(false, aClazz, new Object[]{}, null, 0));
        }
    };


}
