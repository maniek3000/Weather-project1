package com.weather;

import com.weather.forecast.ForecastControler;
import com.weather.forecast.ForecastRepositoryImpl;
import com.weather.forecast.ForecastService;
import com.weather.location.LocationController;
import com.weather.location.LocationRepositoryImpl;
import com.weather.location.LocationService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class WeatherApplication {

    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        LocationRepositoryImpl locationRepositoryImpl = new LocationRepositoryImpl(sessionFactory);
        LocationService locationService = new LocationService(locationRepositoryImpl);
        LocationController locationController = new LocationController(locationService);
        ForecastRepositoryImpl forecastRepositoryImpl = new ForecastRepositoryImpl(sessionFactory);
        ForecastService forecastService = new ForecastService(locationRepositoryImpl, forecastRepositoryImpl);
        ForecastControler forecastControler = new ForecastControler(forecastService);
        UserInterface userInterface = new UserInterface(locationController, forecastControler);
        userInterface.run();


    }
}
