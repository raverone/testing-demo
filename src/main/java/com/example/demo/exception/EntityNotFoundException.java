package com.example.demo.exception;

import java.io.Serial;
import java.math.BigInteger;

public class EntityNotFoundException extends RuntimeException {

  @Serial private static final long serialVersionUID = 1118596067918988159L;

  public EntityNotFoundException(String message) {
    super(message);
  }

  public static EntityNotFoundException airport(BigInteger id) {
    return new EntityNotFoundException("Airport with id '%s' not found.".formatted(id));
  }
}
