package com.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntryService {

    private EntryRepository entryRepository = new EntryRepository();
    private ObjectMapper objectMapper = new ObjectMapper();

    public EntryService() {
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Entry createNewEntry(String cityName, String countryName, Double latitude, Double longitude, String region) {
        if (cityName == null || cityName.isEmpty()) {
            throw new RuntimeException("Nazwa miasta nie może być pusta");
        }
        if (countryName == null || countryName.isEmpty()) {
            throw new RuntimeException("Nazwa kraju nie może być pusta");
        }
        if (latitude < -90 || latitude > 90) {
            throw new RuntimeException("Szerokość geograficzna musi się mieścić w przedziale od -90 do 90");
        }
        if (longitude < -180 || longitude > 180) {
            throw new RuntimeException("Długość geograficzna musi się mieścić w przedziale od -180 do 180");
        }

        Entry entry = new Entry(null, cityName,countryName,latitude,longitude,region);

        return entryRepository.save(entry);

    }

}
