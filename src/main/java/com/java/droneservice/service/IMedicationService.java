package com.java.droneservice.service;

import org.springframework.core.io.Resource;

public interface IMedicationService {

    Resource getMedicationImage(String droneSerialNumber, String medicationCode);
}
