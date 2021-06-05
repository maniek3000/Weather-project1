package com.weather.forecast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.location.Location;
import com.weather.location.LocationRepository;
import lombok.RequiredArgsConstructor;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@RequiredArgsConstructor
public class ForecastService {

    final private LocationRepository locationRepository;
    final private ForecastRepository forecastRepository;
    private ObjectMapper objectMapper = new ObjectMapper();


    public Forecast getForecast(Long locationId, Integer date) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new RuntimeException("Nie ma lokalizacji o id " + locationId));
        LocalDate forecastDate = LocalDate.now().plusDays(date);

        if (forecastRepository.findForecastByLocationAndDate(location, forecastDate).isPresent()) {
            return forecastRepository.findForecastByLocationAndDate(location, forecastDate).get();
        }
        else {

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://api.openweathermap.org/data/2.5/onecall?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&exclude=minutely,hourly&appid=1766fdc82c622688913c1bb885b9bd94"))
                    .build();
            try {
                HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ForecastResponseDTO forecastResponseDTO = objectMapper.readValue(responseBody, ForecastResponseDTO.class);


                Forecast forecast = forecastResponseDTO.getDaily().stream()
                        .filter(sdf -> sdf.getDate().equals(forecastDate))
                        .findFirst()
                        .map(s -> Forecast.builder()
                                .temperature(s.getTemperature().getCelsius())
                                .pressure(s.getPressure())
                                .windSpeed(s.getWindSpeed())
                                .windDeg(s.getWindDeg())
                                .humidity(s.getHumidity())
                                .location(location)
                                .build())
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono prognozy dla wybranej daty"));

                forecast.setLocalDate(forecastDate);
                forecastRepository.saveForecast(forecast);
                return forecast;

            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        }
    }
}
