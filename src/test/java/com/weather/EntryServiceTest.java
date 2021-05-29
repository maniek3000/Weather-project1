package com.weather;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class EntryServiceTest {

    EntryService entryService;

    @Before
    public void setUp() {
        EntryRepository entryRepository = new EntryRepositoryMock();
        entryService = new EntryService(entryRepository);
    }

    @Test
    public void CreateNewEntry_CorrectValues_CreatesNewEntry() {
        //when
        Entry result = entryService.createNewEntry("city", "country", 10.1, 20.2, "region");

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCityName()).isEqualTo("city");
        assertThat(result.getCountryName()).isEqualTo("country");
        assertThat(result.getLatitude()).isEqualTo(10.1);
        assertThat(result.getLongitude()).isEqualTo(20.2);
        assertThat(result.getRegion()).isEqualTo("region");
    }

    @Test
    public void CreateNewEntry_RegionIsEmpty_CreatesNewEntry() {
        //when
        Entry result = entryService.createNewEntry("city", "country", 10.1, 20.2, "");

        //then
        assertThat(result.getRegion()).isNull();
    }

    @Test
    public void CreateNewEntry_RegionIsBlanc_CreatesNewEntry() {
        //when
        Entry result = entryService.createNewEntry("city", "country", 10.1, 20.2, "  ");

        //then
        assertThat(result.getRegion()).isNull();
    }

    @Test
    public void CreateNewEntry_CityIsEmpty_ThrowException() {
        //when
        Throwable result = catchThrowable(() -> entryService.createNewEntry("", "country", 10.1, 20.2, "region"));

        //then
        assertThat(result).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    public void CreateNewEntry_CityIsBlanc_ThrowException() {
        //when
        Throwable result = catchThrowable(() -> entryService.createNewEntry("    ", "country", 10.1, 20.2, "region"));

        //then
        assertThat(result).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    public void CreateNewEntry_CityIsNull_ThrowException() {
        //when
        Throwable result = catchThrowable(() -> entryService.createNewEntry(null, "country", 10.1, 20.2, "region"));

        //then
        assertThat(result).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    public void CreateNewEntry_CountryIsEmpty_ThrowException() {
        //when
        Throwable result = catchThrowable(() -> entryService.createNewEntry("city", "", 10.1, 20.2, "region"));

        //then
        assertThat(result).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    public void CreateNewEntry_CountryIsBlanc_ThrowException() {
        //when
        Throwable result = catchThrowable(() -> entryService.createNewEntry("city", "    ", 10.1, 20.2, "region"));

        //then
        assertThat(result).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    public void CreateNewEntry_CountryIsNull_ThrowException() {
        //when
        Throwable result = catchThrowable(() -> entryService.createNewEntry("city", null, 10.1, 20.2, "region"));

        //then
        assertThat(result).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    public void CreateNewEntry_LatitudeIsToHigh_ThrowException() {
        //when
        Throwable result = catchThrowable(() -> entryService.createNewEntry("city", "country", 91D, 20.2, "region"));

        //then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void CreateNewEntry_LatitudeIsToLow_ThrowException() {
        //when
        Throwable result = catchThrowable(() -> entryService.createNewEntry("city", "country", -91D, 20.2, "region"));

        //then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void CreateNewEntry_LongitudeIsToHigh_ThrowException() {
        //when
        Throwable result = catchThrowable(() -> entryService.createNewEntry("city", "country", 10.1, 181D, "region"));

        //then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void CreateNewEntry_LongitudeIsToLow_ThrowException() {
        //when
        Throwable result = catchThrowable(() -> entryService.createNewEntry("city", "country", 10.1, -181D, "region"));

        //then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

}