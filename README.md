# Android-Service-Controller
The main goal to create this library was to replace many Android Services with one that combines many different functionalities. Creating single Android Service requires a lot of boilerplate and adds asynchronous binding which makes it harder to use.

# How does it work?
AbstractServiceManager
------
In order to create the service you need to extend the AbstractServiceManager. This abstract class works similar to Android's Service. It contains standard lifecycle methods. Main features of AbstractServiceManager: 
- ability to work on separate thread. It's up to a developer to decide if it should or not
- ability to set the thread's priority
- easier communication with service - using the IServiceCommunication 
- ability to stop - using the stopSelf method
- service start delay - by overiding the provideServiceCreateDelay() method

ServiceController
-----
ServiceController can contain many small service classes(classes that extend AbstractServiceController). In Android, for background services that should be running all the time this class should be put in Android's Service. Below you can find an example of this class.
```Java
public class ExampleServiceController extends ServiceController {

    public ExampleServiceController(CommunicationEngine engine, Context context) {
        super(engine);

        addService(new RepetitiveTaskServiceManager(context));
        addService(new ExampleAppStartServiceManager(context));
    }

    @Override
    protected void fillOnAppStartActionsMap(Map<Class<? extends IServiceManager>, IServiceCommunication> onAppStartActionsMap) {
        super.fillOnAppStartActionsMap(onAppStartActionsMap);

        onAppStartActionsMap.put(ExampleAppStartServiceManager.class, new ExampleAppStartServiceManager.AppStartServiceCommunicator());
    }
}
```
Registration of working services happens in the constructor method. fillOnAppStartActionsMap is used only for one time services(services that work only for a specific time). Moreover the one time services can be started also from outside classes(like activities etc.). 

CommunicationEngine
-----
Every ServiceController(with IServiceCommunication) needs a class that is used for easier communication with other classes. In my project I use a CommunicationEngine based on Otto Bus. If you prefer some other library like RxJava, feel free to use it. 

# Download 
You can download library with BusCommunicationEngine through Gradle:
```Java
dependencies {
  compile 'com.tomlocksapps:android-service-controller-bus:0.1.3'
}
```
Or if you want to implement CommunicationEngine on your own use dependency:
```Java
dependencies {
  compile 'com.tomlocksapps:android-service-controller:0.1.3'
}
```

# License
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
