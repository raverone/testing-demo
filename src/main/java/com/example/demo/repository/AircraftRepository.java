package com.example.demo.repository;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Aircraft;
import com.example.demo.model.AircraftType;
import java.math.BigInteger;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, BigInteger> {

  default Aircraft add(Aircraft aircraft) {
    try {
      return save(aircraft);
    } catch (DataIntegrityViolationException e) {
      throw EntityNotFoundException.airport(aircraft.getAirport().getId());
    }
  }

  Long countByAirportId(BigInteger id);

  Long countByAirportIdAndType(BigInteger id, AircraftType type);

  List<Aircraft> findByAirportId(BigInteger id);
}
