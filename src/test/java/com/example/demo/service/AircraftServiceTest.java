package com.example.demo.service;

import static com.example.demo.model.AircraftType.HOT_AIR_BALLOON;
import static com.example.demo.model.AircraftType.PLANE;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.CreateAircraftRequest;
import com.example.demo.exception.NotAllowedException;
import com.example.demo.exception.TooManyAircraftsException;
import com.example.demo.model.Aircraft;
import com.example.demo.model.AircraftType;
import com.example.demo.model.Airport;
import com.example.demo.repository.AircraftRepository;
import com.example.demo.repository.AirportRepository;
import java.math.BigInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AircraftServiceTest {

  AirportRepository airportRepository = mock();
  AircraftRepository aircraftRepository = mock();
  AircraftService service = new AircraftService(airportRepository, aircraftRepository);

  @Test
  void should_get_all_aircrafts() {
    service.getAllAircrafts();

    verify(aircraftRepository, times(1)).findAll();
  }

  static Stream<Arguments> not_valid_requests() {
    return Stream.of(
        of(null, NullPointerException.class),
        of(new CreateAircraftRequest(null, null, null), NullPointerException.class),
        of(new CreateAircraftRequest(null, "Unknown", TEN), IllegalArgumentException.class));
  }

  @ParameterizedTest
  @MethodSource("not_valid_requests")
  void should_throw_exception_if_request_is_not_valid(
      CreateAircraftRequest request, Class<? extends Throwable> expectedExceptionClass) {
    assertThrows(expectedExceptionClass, () -> service.createAircraft(request));
  }

  static Stream<Arguments> excess_requests() {
    return Stream.of(
        of(ONE, 2L, 2L, TooManyAircraftsException.ofType(ONE, PLANE).getMessage()),
        of(TEN, 6L, 5L, TooManyAircraftsException.forAirport(TEN).getMessage()));
  }

  @ParameterizedTest
  @MethodSource("excess_requests")
  void
      should_throw_too_many_aircrafts_exception_if_airport_is_full_or_cant_host_no_more_aircraft_of_type(
          BigInteger airportId,
          Long allAircraftsCount,
          Long aircraftsOfTypeCount,
          String expectedErrorMessage) {

    when(aircraftRepository.countByAirportId(airportId)).thenReturn(allAircraftsCount);
    when(aircraftRepository.countByAirportIdAndType(airportId, PLANE))
        .thenReturn(aircraftsOfTypeCount);

    var ex =
        assertThrows(
            TooManyAircraftsException.class,
            () -> service.createAircraft(new CreateAircraftRequest(null, "PLANE", airportId)));

    assertEquals(expectedErrorMessage, ex.getMessage());
  }

  @Test
  void should_throw_not_allowed_exception() {
    var airportId = ONE;
    when(aircraftRepository.countByAirportId(airportId)).thenReturn(0L);

    var ex =
        assertThrows(
            NotAllowedException.class,
            () ->
                service.createAircraft(
                    new CreateAircraftRequest(null, "HOT_AIR_BALLOON", airportId)));

    assertEquals(
        "Airport with id '%s' unable to host aircrafts of type '%s'"
            .formatted(airportId, HOT_AIR_BALLOON),
        ex.getMessage());
  }

  static Stream<Arguments> valid_requests() {
    return Stream.of(
        of(new CreateAircraftRequest(null, "PLANE", ONE)),
        of(new CreateAircraftRequest(null, "PLANE", TEN)));
  }

  @ParameterizedTest
  @MethodSource("valid_requests")
  void should_create_new_aircraft(CreateAircraftRequest request) {
    var airportId = request.airportId();
    var aircraftType = AircraftType.valueOf(request.type());
    var airport = new Airport(airportId, "code", "name");
    when(aircraftRepository.countByAirportId(airportId)).thenReturn(0L);
    when(aircraftRepository.countByAirportIdAndType(airportId, aircraftType)).thenReturn(0L);
    when(airportRepository.getReferenceById(airportId)).thenReturn(airport);

    service.createAircraft(request);

    verify(aircraftRepository, times(1)).save(new Aircraft(request.name(), aircraftType, airport));
  }
}
