package com.example.demo.dto;

import com.example.demo.model.Aircraft;
import java.math.BigInteger;

public record AircraftDto(BigInteger id, String type, String name, String airportName) {
  public AircraftDto(Aircraft aircraft) {
    this(
        aircraft.getId(),
        aircraft.getType().name(),
        aircraft.getName(),
        aircraft.getAirport().getName());
  }
}
