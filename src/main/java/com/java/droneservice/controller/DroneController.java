package com.java.droneservice.controller;

import com.java.droneservice.api.DroneApi;
import com.java.droneservice.model.DroneDTO;
import com.java.droneservice.model.MedicationDTO;
import com.java.droneservice.service.DroneService;
import com.java.droneservice.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST Controller for Drone Service API
 */

@RestController
public class DroneController implements DroneApi {

    @Autowired
    private DroneService droneService;

    @Autowired
    private MedicationService medicationService;

    @Override
    public ResponseEntity<List<DroneDTO>> getAllDrones() {
        return ResponseEntity.ok(droneService.getAllDrones());
    }

    @Override
    public ResponseEntity<List<DroneDTO>> registerDrone(DroneDTO drone) {
        return ResponseEntity.ok(droneService.registerDrone(drone));
    }

    @Override
    public ResponseEntity<DroneDTO> loadMedication(String serialNumber, String medicationPayload, MultipartFile file) {
        return ResponseEntity.ok(droneService.loadMedication(serialNumber, medicationPayload, file));
    }

    @Override
    public ResponseEntity<Resource> getMedicationImage(String serialNumber, String medicationCode) {

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + medicationCode + ".png\"")
                .body(medicationService.getMedicationImage(serialNumber, medicationCode));
    }

    @Override
    public ResponseEntity<List<MedicationDTO>> getPayloadByDroneSerialNumber(String serialNumber) {
        return ResponseEntity.ok(droneService.getPayload(serialNumber));
    }

    @Override
    public ResponseEntity<Void> deleteDrone() {
        return DroneApi.super.deleteDrone();
    }
}
