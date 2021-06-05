package com.weather.forecast;

import com.weather.location.Location;

import java.time.LocalDate;
import java.util.Optional;

public interface ForecastRepository {
    public Optional<Forecast> findForecastByLocationAndDate(Location location, LocalDate date);
    public Forecast saveForecast(Forecast forecast);

}
