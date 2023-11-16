package com.example.demo.repository;

import com.example.demo.model.Aircraft;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, BigInteger> {

}
