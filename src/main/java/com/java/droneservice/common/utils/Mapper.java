package com.java.droneservice.common.utils;

import com.java.droneservice.model.DroneDTO;
import com.java.droneservice.model.MedicationDTO;
import com.java.droneservice.repository.entities.DroneEntity;
import com.java.droneservice.repository.entities.MedicationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.java.droneservice.common.utils.Constants.modelMap;

@Component
public class Mapper {

    private final ModelMapper modelMapper;

    @Autowired
    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<DroneDTO> mapToDroneDTO(List<DroneEntity> drones) {
        return drones.stream()
                .map(this::mapToSingleDroneDTO)
                .collect(Collectors.toList());
    }

    public DroneDTO mapToSingleDroneDTO(DroneEntity drone) {
        DroneDTO dto = modelMapper.map(drone, DroneDTO.class);
        dto.setWeightLimit(modelMap.get(drone.getModel()));

        // Manually fix the payload mapping
        List<MedicationDTO> payloadDTO = drone.getPayload().stream()
                .map(med -> mapToMedicationDTO(med, drone.getSerialNumber()))
                .collect(Collectors.toList());

        dto.setPayload(payloadDTO);
        return dto;
    }

    public MedicationDTO mapToMedicationDTO(MedicationEntity entity, String droneSerialNumber) {
        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setName(entity.getMedicationName());
        medicationDTO.setWeight(entity.getWeight());
        medicationDTO.setCode(entity.getCode());
        medicationDTO.setImageUrl(URI.create("/drone/" + droneSerialNumber + "/payload/" + entity.getCode() + "/image"));
        return medicationDTO;
    }

    public DroneEntity mapToDroneEntity(DroneDTO drone) {

        try {
            return modelMapper.map(drone, DroneEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MedicationEntity mapToMedicationEntity(MedicationDTO medicationDTO, byte[] image) {

        try {
            return MedicationEntity.builder()
                    .medicationName(medicationDTO.getName())
                    .weight(medicationDTO.getWeight())
                    .code(medicationDTO.getCode())
                    .image(image)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
