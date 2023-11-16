package com.example.demo.controller;

import com.example.demo.dto.AircraftDto;
import com.example.demo.model.Aircraft;
import com.example.demo.repository.AircraftRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AircraftController {

  private final AircraftRepository aircraftRepository;

  @GetMapping
  public List<AircraftDto> greeting() {
    return aircraftRepository.findAll().stream().map(AircraftController::mapToProductDto).toList();
  }

  @PostMapping
  public AircraftDto create(@RequestBody AircraftDto aircraftDto) {
    Aircraft aircraft = aircraftRepository.save(new Aircraft(aircraftDto.name()));
    return mapToProductDto(aircraft);
  }

  private static AircraftDto mapToProductDto(Aircraft model) {
    return new AircraftDto(model.getId(), model.getName());
  }
}
