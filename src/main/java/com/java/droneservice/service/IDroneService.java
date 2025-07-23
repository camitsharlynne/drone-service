package com.java.droneservice.service;

import com.java.droneservice.model.DroneDTO;
import com.java.droneservice.model.MedicationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDroneService {

    List<DroneDTO> getAllDrones();
    List<DroneDTO> registerDrone(DroneDTO drone);
    DroneDTO loadMedication(String serialNumber, String medicationPayload, MultipartFile file);
    List<MedicationDTO> getPayload(String droneSerialNumber);

}
