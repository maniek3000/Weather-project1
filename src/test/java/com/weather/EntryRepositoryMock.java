package com.weather;

public class EntryRepositoryMock implements EntryRepository {

    @Override
    public Entry save(Entry entry) {
        entry.setId(1L);
        return entry;
    }
}
