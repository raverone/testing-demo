package com.example.demo.exception;

import com.example.demo.model.AircraftType;
import java.io.Serial;
import java.math.BigInteger;

public class TooManyAircraftsException extends RuntimeException {

  private static String BASE_MESSAGE =
      "Airport '%s' already has maximum allowed number of aircrafts";

  @Serial private static final long serialVersionUID = 5855044160059750829L;

  public TooManyAircraftsException(String message) {
    super(message);
  }

  public static TooManyAircraftsException forAirport(BigInteger id) {
    return new TooManyAircraftsException(BASE_MESSAGE.formatted(id));
  }

  public static TooManyAircraftsException ofType(BigInteger id, AircraftType type) {
    return new TooManyAircraftsException((BASE_MESSAGE + " of type '%s'").formatted(id, type));
  }
}
