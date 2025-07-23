package com.java.droneservice.repository;

import com.java.droneservice.model.DroneStateEnum;
import com.java.droneservice.repository.entities.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DronesRepository extends JpaRepository<DroneEntity, Long> {

    List<DroneEntity> findAll();

    boolean existsBySerialNumber(String serialNumber);

    DroneEntity findBySerialNumber(String serialNumber);

    List<DroneEntity> findByState(String state);
}
