# weather_app

this is a sample project to load weather data with offline access
on this project we splitter the application on 2 modules: application and library

## Library module
In this module, we load weather data from WeatherOpenMap api server and save it on database to have offline access

We have used:
 - Room to save data on database
 - LiveData to load data from database and make observable to refresh data on real time
 - Coroutine to load data from network

![Sequence diagram](/docs/sequence_diagram.png)
 
### integration
to integrate the SDK on your application: 

//TODO put gradle dependencies link

then put you OpenWeatherMap Api_Key on the manifest file like this
```xml
<meta-data
    android:name="com.weather.sdk.library_access_key"
    android:value="097f7f170809a98b2d18d9084cf8f885" />
```  

and within you application class add these lines:
```kotlin
val weatherSDK by lazy { WeatherSDK(this)}
```

with in the library we expose only 3 functions:
* insert new location:
we use this function to insert a new location on the database

```kotlin
insertLocation(location: Location)
```


* fetch locations:
this function help to load already saved locations from database
```kotlin
fun getLocations()
```

* fetch weathers data for a location

```kotlin
getWeathers(location: Location)
```


