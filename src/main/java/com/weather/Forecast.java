package com.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double temperature;

    private Double pressure;

    private Double windSpeed;

    private Double windDeg;

    private Double humidity;

    private LocalDate localDate;

    @ManyToOne
       private Location location;



}
