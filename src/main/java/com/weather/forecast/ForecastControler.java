package com.weather.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class ForecastControler {


    private final ForecastService forecastService;
    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());;

    public ForecastControler(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    public String getForecast(Long locationId, int day) {
        try {
            Forecast forecast = forecastService.getForecast(locationId, day);
            return objectMapper.writeValueAsString(forecast);
        } catch (Exception e) {
            return "{\"error message\": \"" + e.getMessage() + "\"}";
        }
    }
}

