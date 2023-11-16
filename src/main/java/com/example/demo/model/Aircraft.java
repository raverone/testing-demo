package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.math.BigInteger;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Aircraft {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aircraft_id_seq")
  @SequenceGenerator(name="aircraft_id_seq", sequenceName = "aircraft_id_seq", allocationSize = 1)
  private BigInteger id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private AircraftType type;

  @ManyToOne(fetch = FetchType.LAZY)
  private Airport airport;

  public Aircraft(String name, AircraftType type, Airport airport) {
    this.name = name;
    this.type = type;
    this.airport = airport;
  }
}
