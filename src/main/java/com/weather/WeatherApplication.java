package com.weather;

public class WeatherApplication {

    public static void main(String[] args) {
        LocationRepositoryImpl entryRepositoryImpl = new LocationRepositoryImpl();
        LocationService locationService = new LocationService(entryRepositoryImpl);
        LocationController locationController = new LocationController(locationService);
        UserInterface userInterface = new UserInterface(locationController);
        userInterface.run();
    }
}
