package com.example.demo.exception;

import com.example.demo.model.AircraftType;
import java.io.Serial;
import java.math.BigInteger;

public class NotAllowedException extends RuntimeException {

  @Serial private static final long serialVersionUID = 7114357413677563511L;

  public NotAllowedException(String message) {
    super(message);
  }

  public static NotAllowedException ofAircraftType(BigInteger id, AircraftType type) {
    return new NotAllowedException(
        "Airport with id '%s' unable to host aircrafts of type '%s'".formatted(id, type));
  }
}
