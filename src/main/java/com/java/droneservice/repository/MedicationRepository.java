package com.java.droneservice.repository;

import com.java.droneservice.repository.entities.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {

    Optional<MedicationEntity> findByDroneSerialNumberAndCode(String serialNumber, String code);

}
