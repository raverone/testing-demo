package com.example.demo.controller;

import com.example.demo.dto.AircraftDto;
import com.example.demo.dto.CreateAircraftRequest;
import com.example.demo.service.AircraftService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AircraftController {

  private final AircraftService aircraftService;

  @GetMapping
  public List<AircraftDto> getAllAircrafts() {
    return aircraftService.getAllAircrafts().stream().map(AircraftDto::new).toList();
  }

  @PostMapping
  public AircraftDto create(@RequestBody CreateAircraftRequest request) {
    return new AircraftDto(aircraftService.createAircraft(request));
  }
}
