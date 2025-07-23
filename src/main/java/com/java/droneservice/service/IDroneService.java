package com.java.droneservice.service;

import com.java.droneservice.model.DroneDTO;
import com.java.droneservice.model.MedicationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for DroneService.
 */
public interface IDroneService {

    /**
     * Retrieves the available drones registered.
     * @return DroneDTO
     */
    List<DroneDTO> getAllDrones();

    /**
     * Registers drone.
     * @return DroneDTO list
     */
    List<DroneDTO> registerDrone(DroneDTO drone);

    /**
     * Loads medication as payload to the drone.
     * @return DroneDTO
     */
    DroneDTO loadMedication(String serialNumber, String medicationPayload, MultipartFile file);

    /**
     * Retrieves the payloads of a specific drone by its serialNumber.
     * @return MedicationDTO
     */
    List<MedicationDTO> getPayload(String droneSerialNumber);

}
