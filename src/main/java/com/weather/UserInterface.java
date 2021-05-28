package com.weather;

import java.util.Scanner;

public class UserInterface {

    private EntryController entryController = new EntryController();

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
                    //todo
                    break;
                case 3:
                    //todo
                    break;
                case 0:
                    return;
            }
        }
    }

     private void addNewEntry(){
        Scanner scanner = new Scanner(System.in);
         System.out.println("Podaj miasto");
         String city = scanner.nextLine();
         System.out.println("Podaj kraj");
         String country = scanner.nextLine();
         System.out.println("Podaj szerokość geograficzną");
         Double latitude = scanner.nextDouble();
         System.out.println("Podaj długość geograficzną");
         Double longitude = scanner.nextDouble();
         System.out.println("Podaj region");
         String region = scanner.nextLine();
         String httpResponseBody = entryController.createNewEntry(city,country,latitude,longitude,region);
         System.out.println("Odpowiedź z servera: "+ httpResponseBody);

    }
}
