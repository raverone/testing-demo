package com.example.demo.repository;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Airport;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, BigInteger> {

  default Airport getById(BigInteger id) {
    return findById(id).orElseThrow(() -> EntityNotFoundException.airport(id));
  }
}
