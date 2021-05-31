package com.weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationRepositoryMock implements LocationRepository {

    private List<Location> locations = new ArrayList<>();

    @Override
    public Location save(Location location) {
        location.setId(1L);
        locations.add(location);
        return location;
    }

    @Override
    public List<Location> getAllLocation() {
        return locations;
    }

    @Override
    public Optional<Location> findById(Long id) {
                return locations.stream()
                .filter(l->l.getId().equals(id))
                .findFirst();
    }

    //todo mozna zrobic metode clear czyszczaca liste
}
