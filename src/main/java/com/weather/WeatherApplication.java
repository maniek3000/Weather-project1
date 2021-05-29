package com.weather;

public class WeatherApplication {

    public static void main(String[] args) {
        EntryRepositoryImpl entryRepositoryImpl = new EntryRepositoryImpl();
        EntryService entryService = new EntryService(entryRepositoryImpl);
        EntryController entryController = new EntryController(entryService);
        UserInterface userInterface = new UserInterface(entryController);
        userInterface.run();
    }
}
