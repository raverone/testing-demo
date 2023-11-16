package com.example.demo.repository;

import com.example.demo.model.AircraftType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AircraftTypeConverter implements AttributeConverter<AircraftType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(AircraftType attribute) {
    return attribute == null ? null : attribute.getCode();
  }

  @Override
  public AircraftType convertToEntityAttribute(Integer dbData) {
    return AircraftType.byCode(dbData);
  }
}
