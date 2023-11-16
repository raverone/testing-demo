package com.example.demo.dto;

import java.math.BigInteger;

public record CreateAircraftRequest(String name, String type, BigInteger airportId) {}
