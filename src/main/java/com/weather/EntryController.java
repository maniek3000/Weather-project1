package com.weather;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EntryController {

    private EntryService entryService = new EntryService();
    private ObjectMapper objectMapper = new ObjectMapper();

    public String createNewEntry(String cityName, String countryName, Double latitude, Double longitude, String region) {
        try {
            Entry newEntry = entryService.createNewEntry(cityName, countryName, latitude, longitude, region);
            return objectMapper.writeValueAsString(newEntry);
        } catch (Exception e) {
            return "{\"error message\": \"" + e.getMessage() + "\"}";
        }
    }
}
