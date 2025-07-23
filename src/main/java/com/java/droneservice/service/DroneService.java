package com.java.droneservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.droneservice.common.utils.Mapper;
import com.java.droneservice.common.utils.ValidDroneStateEnum;
import com.java.droneservice.exceptions.DroneServiceException;
import com.java.droneservice.model.DroneDTO;
import com.java.droneservice.model.DroneModelEnum;
import com.java.droneservice.model.DroneStateEnum;
import com.java.droneservice.model.MedicationDTO;
import com.java.droneservice.repository.DronesRepository;
import com.java.droneservice.repository.entities.DroneEntity;
import com.java.droneservice.repository.entities.MedicationEntity;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.java.droneservice.common.utils.Constants.modelMap;

/**
 * Service class containing business logic
 * for drones and payloads.
 */
@Service
public class DroneService implements IDroneService {

    private final Mapper mapper;
    private final ObjectMapper objectMapper;
    private final DronesRepository dronesRepository;

    @Autowired
    public DroneService(DronesRepository dronesRepository, Mapper mapper, ObjectMapper objectMapper) {
        this.dronesRepository = dronesRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<DroneDTO> getAllDrones() {
        return mapper.mapToDroneDTO(dronesRepository.findAll());
    }

    @Override
    public List<DroneDTO> registerDrone(DroneDTO drone) {

        try {
            if (dronesRepository.existsBySerialNumber(drone.getSerialNumber())) {
                throw new DroneServiceException("Drone with this serial number already exists!");
            }

            DroneEntity droneEntity = mapper.mapToDroneEntity(drone);
            dronesRepository.save(droneEntity);
            return mapper.mapToDroneDTO(dronesRepository.findAll());
        } catch (Exception e) {
            throw new DroneServiceException("There was an error in drone registration!", e);
        }
    }

    @Override
    public DroneDTO loadMedication(String serialNumber, String medicationPayload, MultipartFile file) {

        try {
            // Parse JSON
            MedicationDTO medicationDTO = objectMapper.readValue(medicationPayload, MedicationDTO.class);

            // get bytes from file
            byte[] byteArr = file.getBytes();

            // Check if drone exists
            DroneEntity droneEntity = dronesRepository.findBySerialNumber(serialNumber);
            if (droneEntity == null) {
                throw new DroneServiceException(String.format("Drone with serial number %s does not exist!", serialNumber));
            }

            // Check drone state, battery life, and if overloaded
            validateDroneLife(droneEntity, medicationDTO.getWeight());

            // Map and add medication to payload
            droneEntity.addItem(mapper.mapToMedicationEntity(medicationDTO, byteArr));
            droneEntity.setState(isMaxLoadReached(droneEntity) ? DroneStateEnum.LOADED : DroneStateEnum.LOADING); // set to loading state
            dronesRepository.save(droneEntity);

            return mapper.mapToSingleDroneDTO(droneEntity);

        } catch (IOException e) {
            throw new DroneServiceException("File upload failed", e);
        }
    }

    @Override
    public List<MedicationDTO> getPayload(String droneSerialNumber) {

        // Check if drone exists
        DroneEntity droneEntity = dronesRepository.findBySerialNumber(droneSerialNumber);
        if (droneEntity == null) {
            throw new DroneServiceException(String.format("Drone with serial number %s does not exist!", droneSerialNumber));
        }

        // Get payload
        return droneEntity.getPayload().stream()
                .map(payload -> mapper.mapToMedicationDTO(payload, droneSerialNumber))
                .collect(Collectors.toList());
    }

    public void validateDroneLife(DroneEntity droneEntity, int weight) {

        // Drone should be either in IDLE, LOADING, LOADED state
        if (!EnumUtils.isValidEnum(ValidDroneStateEnum.class, droneEntity.getState().getValue())) {
            throw new DroneServiceException(String.format("Drone not valid to be loaded! In %s state.", droneEntity.getState()));
        }

        // Drone should be checked for overloading
        if (isOverloaded(droneEntity, weight)) {
            throw new DroneServiceException("Drone not valid to be loaded! Drone is overloaded!");
        }

        // Drone should have battery level to > 25%
        if (droneEntity.getBatteryCapacity() < 25) {
            throw new DroneServiceException(String.format("Drone not valid to be loaded! Drone's battery capacity at %s", droneEntity.getBatteryCapacity()));
        }

    }

    public boolean isOverloaded(DroneEntity droneEntity, int payloadWeight) {

        int totalWeight = droneEntity.getPayload().stream()
                .map(MedicationEntity::getWeight)
                .reduce(0, Integer::sum);

        return (totalWeight+payloadWeight) > modelMap.get(droneEntity.getModel());
    }

    public boolean isMaxLoadReached(DroneEntity droneEntity) {
        int totalWeight = droneEntity.getPayload().stream()
                .map(MedicationEntity::getWeight)
                .reduce(0, Integer::sum);

        return totalWeight == modelMap.get(droneEntity.getModel());
    }
}
