package com.java.droneservice.service;

import com.java.droneservice.exceptions.MedicationServiceException;
import com.java.droneservice.repository.MedicationRepository;
import com.java.droneservice.repository.entities.MedicationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class MedicationService implements IMedicationService {

    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Override
    public Resource getMedicationImage(String droneSerialNumber, String medicationCode) {

        MedicationEntity medication = medicationRepository
                .findByDroneSerialNumberAndCode(droneSerialNumber, medicationCode)
                .orElseThrow(() -> new MedicationServiceException("Medication not found!"));

        byte[] imageBytes = medication.getImage();

        return new ByteArrayResource(imageBytes);
    }

}
