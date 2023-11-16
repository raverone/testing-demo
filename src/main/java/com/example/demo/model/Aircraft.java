package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;

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

  public Aircraft(String name) {
    this.name = name;
  }
}
