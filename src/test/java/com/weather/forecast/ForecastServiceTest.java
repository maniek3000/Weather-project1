package com.weather.forecast;


import com.weather.location.LocationRepositoryMock;
import com.weather.location.Location;
import com.weather.location.LocationService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ForecastServiceTest {

    LocationRepositoryMock locationRepositoryMock;
    ForecastRepositoryMock forecastRepositoryMock;
    ForecastService forecastService;
    LocationService locationService;

    @Before
    public void setUp() {
        locationRepositoryMock = new LocationRepositoryMock();
        forecastRepositoryMock = new ForecastRepositoryMock();
        forecastService = new ForecastService(locationRepositoryMock, forecastRepositoryMock);
        locationService = new LocationService(locationRepositoryMock);
    }

    @Test
    public void saveForecast() {
        //when
        Location location = locationService.createNewEntry("city", "country", 10.1, 20.2, "region");
        location.setId(1L);
        Forecast forecast = forecastService.getForecast(1L, 2);
        //then
        assertThat(forecastRepositoryMock.getForecasts().size()).isEqualTo(1);
    }


    @Test
    public void getForecast_FromOutsideServer() {
        //when
        locationRepositoryMock.save(new Location(1L, "city", "country", 10.1, 20.2, "region"));
        Forecast forecast = forecastService.getForecast(1L, 2);

        //then
        assertThat(forecast).isNotNull();
        assertThat(forecast.getId()).isEqualTo(1L);
        assertThat(forecast.getLocalDate()).isEqualTo(LocalDate.now().plusDays(2));
    }

    @Test
    public void getForecast_throwException_whenLocationIsNotInDataBase(){
        //when
        Throwable result = catchThrowable(() -> forecastService.getForecast(1L, 2));

        //then
        assertThat(result).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    public void getForecast_throwException_whenDateIsToFar(){
        //when
        locationRepositoryMock.save(new Location(1L, "city", "country", 10.1, 20.2, "region"));
        Throwable result = catchThrowable(() -> forecastService.getForecast(1L, 8));

        //then
        assertThat(result).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    public void getForecast_FromDataBase() {
        //when
        locationRepositoryMock.save(new Location(1L, "city", "country", 10.1, 20.2, "region"));
        Forecast forecast1 = forecastService.getForecast(1L, 2);
        Forecast forecast2 = forecastService.getForecast(1L, 3);
        Forecast forecast3 = forecastService.getForecast(1L, 5);
        forecastRepositoryMock.saveForecast(forecast1);
        forecastRepositoryMock.saveForecast(forecast2);
        forecastRepositoryMock.saveForecast(forecast3);

        //then
        assertThat(forecastService.getForecast(1L,3)).isEqualTo(forecast2);

    }

}