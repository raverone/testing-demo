package com.example.demo.model;

import lombok.Getter;

@Getter
public enum AircraftType {
  HOT_AIR_BALLOON(1),
  AIRSHIP(2),
  PLANE(3),
  HELICOPTER(4);

  private int code;

  AircraftType(int code) {
    this.code = code;
  }

  public static AircraftType byCode(int code) {
    for (AircraftType value : AircraftType.values()) {
      if (value.code == code) {
        return value;
      }
    }
    throw new IllegalArgumentException("Unknown aircraft type with code '%s'".formatted(code));
  }
}
