package com.example.demo.service;

import static com.example.demo.model.AircraftType.AIRSHIP;
import static com.example.demo.model.AircraftType.HELICOPTER;
import static com.example.demo.model.AircraftType.HOT_AIR_BALLOON;
import static com.example.demo.model.AircraftType.PLANE;
import static java.util.Map.of;

import com.example.demo.dto.CreateAircraftRequest;
import com.example.demo.exception.NotAllowedException;
import com.example.demo.exception.TooManyAircraftsException;
import com.example.demo.model.Aircraft;
import com.example.demo.model.AircraftType;
import com.example.demo.repository.AircraftRepository;
import com.example.demo.repository.AirportRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AircraftService {

  private static final int MAX_AIRCRAFTS_PER_AIRPORT = 5;

  // Rules:
  //  - if airport is not present in this map then this airport can have any amount of aircrafts of
  // any type
  //  - if airport is present in this map and some aircraft type is not specified for this airport
  // then this airport can not have any aircrafts of this type
  private static final Map<BigInteger, Map<AircraftType, Integer>>
      AIRCRAFT_TYPES_DISTRIBUTION_PER_AIRPORT =
          of(
              BigInteger.ONE,
              of(PLANE, 2, HELICOPTER, 1, AIRSHIP, 1),
              BigInteger.TWO,
              of(HOT_AIR_BALLOON, 3, AIRSHIP, 1),
              BigInteger.valueOf(3),
              of(PLANE, 2, HELICOPTER, 1, AIRSHIP, 1, HOT_AIR_BALLOON, 1));

  private final AirportRepository airportRepository;
  private final AircraftRepository aircraftRepository;

  public List<Aircraft> getAllAircrafts() {
    return aircraftRepository.findAll();
  }

  public Aircraft createAircraft(CreateAircraftRequest request) {
    ensureAirportIsNotFull(request.airportId());

    ensureAirportCanHostNewAircraft(request);

    return aircraftRepository.save(
        new Aircraft(
            request.name(),
            AircraftType.valueOf(request.type()),
            airportRepository.getReferenceById(request.airportId())));
  }

  private void ensureAirportIsNotFull(BigInteger airportId) {
    if (aircraftRepository.countByAirportId(airportId) > MAX_AIRCRAFTS_PER_AIRPORT) {
      throw TooManyAircraftsException.forAirport(airportId);
    }
  }

  private void ensureAirportCanHostNewAircraft(CreateAircraftRequest request) {
    var aircraftTypesDistribution =
        AIRCRAFT_TYPES_DISTRIBUTION_PER_AIRPORT.get(request.airportId());
    if (aircraftTypesDistribution == null) {
      return;
    }

    var aircraftType = AircraftType.valueOf(request.type());
    var allowedCount = aircraftTypesDistribution.get(aircraftType);
    if (allowedCount == null) {
      throw NotAllowedException.ofAircraftType(request.airportId(), aircraftType);
    }
    if (aircraftRepository.countByAirportIdAndType(request.airportId(), aircraftType)
        >= allowedCount) {
      throw TooManyAircraftsException.ofType(request.airportId(), aircraftType);
    }
  }
}
