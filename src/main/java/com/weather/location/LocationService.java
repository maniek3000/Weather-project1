package com.weather.location;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Collection;


public class LocationService {

    private final LocationRepository locationRepository;
    private ObjectMapper objectMapper = new ObjectMapper();


    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Location createNewEntry(String cityName, String countryName, Double latitude, Double longitude, String region) {
        if (cityName == null || cityName.isBlank()) {
            throw new RuntimeException("Nazwa miasta nie może być pusta");
        }
        if (countryName == null || countryName.isBlank()) {
            throw new RuntimeException("Nazwa kraju nie może być pusta");
        }
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Szerokość geograficzna musi się mieścić w przedziale od -90 do 90");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Długość geograficzna musi się mieścić w przedziale od -180 do 180");
        }
        if (region.isBlank()) {
            region = null;
        }

        Location location = new Location(null, cityName, countryName, latitude, longitude, region);

        return locationRepository.save(location);
    }

    public Collection<Location> getAllLocations() {
        return locationRepository.getAllLocation();
    }
}
