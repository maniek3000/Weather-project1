package com.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntryService {

    private final EntryRepository entryRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Entry createNewEntry(String cityName, String countryName, Double latitude, Double longitude, String region) {
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
        if (region.isBlank()){
            region=null;
        }

        Entry entry = new Entry(null, cityName,countryName,latitude,longitude,region);

        return entryRepository.save(entry);
    }
}
