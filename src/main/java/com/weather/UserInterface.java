package com.weather;

import com.weather.forecast.ForecastControler;
import com.weather.location.LocationController;

import java.util.Scanner;

public class UserInterface {

    private final LocationController locationController;
    private final ForecastControler forecastControler;


    public UserInterface(LocationController locationController, ForecastControler forecastControler) {
        this.locationController = locationController;
        this.forecastControler = forecastControler;
    }

    public void run() {
        System.out.println("Aplikacja jest uruchomiona!");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Witaj w elektornicznej bazie pogodowej, co chcesz zrobić?");
            System.out.println("1. Dodać nową lokalizację");
            System.out.println("2. Wyświetlić wszystkie dodane lokalizacje");
            System.out.println("3. Dowiedzieć się jaka jest pogoda w podanej lokalizacji");
            System.out.println("0. Zamknąć aplikację");

            int response = scanner.nextInt();

            switch (response) {
                case 1:
                    addNewEntry();
                    break;
                case 2:
                    getAllLocations();
                    break;
                case 3:
                    getForecast();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void getForecast() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id miasta:");
        Long id = scanner.nextLong();
        System.out.println("Podaj na ile dni w przód chcesz otrzymać prognozę (0- na dziś, 1- na jutro, 2- na pojutrze itd. --do 7 dni od dziś--)");
        int day = scanner.nextInt();
        String forecast = forecastControler.getForecast(id, day);
        System.out.println("Odpowiedź z servera: " + forecast);
    }

    private String getAllLocations() {
        String string = locationController.getAllLocations();
        System.out.println(string);
        return string;
    }


    private void addNewEntry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj miasto");
        String city = scanner.nextLine();
        System.out.println("Podaj kraj");
        String country = scanner.nextLine();
        System.out.println("Podaj region");
        String region = scanner.nextLine();
        System.out.println("Podaj szerokość geograficzną");
        Double latitude = scanner.nextDouble();
        System.out.println("Podaj długość geograficzną");
        Double longitude = scanner.nextDouble();


        String httpResponseBody = locationController.createNewEntry(city, country, latitude, longitude, region);
        System.out.println("Odpowiedź z servera: " + httpResponseBody);

    }
}
