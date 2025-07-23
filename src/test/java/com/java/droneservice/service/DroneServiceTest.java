package com.java.droneservice.service;

import com.java.droneservice.common.utils.Mapper;
import com.java.droneservice.model.DroneDTO;
import com.java.droneservice.model.DroneModelEnum;
import com.java.droneservice.model.DroneStateEnum;
import com.java.droneservice.repository.DronesRepository;
import com.java.droneservice.repository.entities.DroneEntity;
import com.java.droneservice.repository.entities.MedicationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DroneServiceTest {

    @Mock
    private Mapper mapper;

    @Mock
    private DronesRepository dronesRepository;

    @InjectMocks
    private DroneService droneService;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldRegisterDrone() {

        List<DroneDTO> droneList = createDrones();

        DroneEntity droneEntity = createDroneEntity();

        when(mapper.mapToDroneEntity(any(DroneDTO.class))).thenReturn(droneEntity);
        when(mapper.mapToDroneDTO(anyList())).thenReturn(droneList);

        List<DroneDTO> result = droneService.registerDrone(droneList.get(0));

        assertEquals(droneList, result);
    }

    @Test
    public void shouldNotRegisterDrone_invalidPayloadMedication() {

        String code = "PAR*85";

        MedicationEntity meds = MedicationEntity.builder()
                .code(code)
                .build();

        Set<ConstraintViolation<MedicationEntity>> violations = validator.validate(meds);

        System.out.println(violations);
        assertEquals(3, violations.size());
    }

    @Test
    public void shouldNotRegisterDrone_invalidDroneDetails() {

        DroneEntity drone = DroneEntity.builder()
                .serialNumber("ABCD-1234-EFGH-5678")
                .model(DroneModelEnum.CRUISER_WEIGHT)
                .state(DroneStateEnum.LOADING)
                .batteryCapacity(-1)
                .payload(new ArrayList<>())
                .build();

        Set<ConstraintViolation<DroneEntity>> violations = validator.validate(drone);

        System.out.println(violations);
        assertEquals(1, violations.size());
    }

    public List<DroneDTO> createDrones() {
        List<DroneDTO> droneList = new ArrayList<>();

        DroneDTO registeredDrone = new DroneDTO();
        registeredDrone.setModel(DroneModelEnum.CRUISER_WEIGHT);
        registeredDrone.setState(DroneStateEnum.LOADING);
        registeredDrone.setSerialNumber("SN12345678");
        registeredDrone.setBatteryCapacity(100);

        droneList.add(registeredDrone);
        return droneList;
    }

    public DroneEntity createDroneEntity() {

        return DroneEntity.builder()
                .id(1L)
                .state(DroneStateEnum.LOADING)
                .model(DroneModelEnum.CRUISER_WEIGHT)
                .serialNumber("SN12345678")
                .batteryCapacity(100)
                .build();
    }

}