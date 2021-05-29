package com.weather;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationController {

    private final LocationService locationService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    public String createNewEntry(String cityName, String countryName, Double latitude, Double longitude, String region) {
        try {
            Location newLocation = locationService.createNewEntry(cityName, countryName, latitude, longitude, region);
            return objectMapper.writeValueAsString(newLocation);
        } catch (Exception e) {
            return "{\"error message\": \"" + e.getMessage() + "\"}";
        }
    }
    public void getAllLocations(){
        locationService.getAllLocations();
    }
}