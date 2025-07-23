package com.java.droneservice.service;

import org.springframework.core.io.Resource;

/**
 * Service interface for MedicationService.
 */
public interface IMedicationService {

    /**
     * Retrieves the payload image for the medication.
     * @param droneSerialNumber - the drone's serial number
     * @param medicationCode - the medication's code
     * @return resource/image
     */
    Resource getMedicationImage(String droneSerialNumber, String medicationCode);
}
