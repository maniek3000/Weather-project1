package com.weather.forecast;

import com.weather.forecast.Forecast;
import com.weather.forecast.ForecastRepository;
import com.weather.location.Location;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ForecastRepositoryMock implements ForecastRepository {

    private List<Forecast> forecasts = new ArrayList<>();

    @Override
    public Optional<Forecast> findForecastByLocationAndDate(Location location, LocalDate date) {
        for (Forecast f : forecasts) {
            if (f.getLocation().equals(location) && f.getLocalDate().equals(date)) {
                return Optional.of(f);
            }
        }
        return Optional.empty();
    }

    @Override
    public Forecast saveForecast(Forecast forecast) {
        forecast.setId(1);
        forecasts.add(forecast);
        return forecast;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }
}
