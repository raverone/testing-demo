package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airport_id_seq")
  @SequenceGenerator(name="airport_id_seq", sequenceName = "airport_id_seq", allocationSize = 1)
  private BigInteger id;

  @Column(nullable = false)
  private String code;

  @Column(nullable = false)
  private String name;
}
