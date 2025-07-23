package com.java.droneservice.repository.entities;

import com.java.droneservice.model.DroneModelEnum;
import com.java.droneservice.model.DroneStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DRONES")
public class DroneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SERIAL_NUMBER")
    @NotNull(message = "Serial Number cannot be null")
    @Pattern(regexp = "^[A-Z0-9]{4}(-[A-Z0-9]{4}){3}$")
    @Size(min = 6, max = 100)
    private String serialNumber;

    @Column(name = "MODEL")
    @NotNull(message = "Model cannot be null")
    @Enumerated(EnumType.STRING)
    private DroneModelEnum model;

    @Column(name = "BATTERY_CAPACITY")
    @Min(value = 1, message = "Battery capacity should not be less than 0")
    @Max(value = 100, message = "Battery capacity should not be greater than 100")
    @NotNull(message = "Battery capacity cannot be null")
    private int batteryCapacity;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "State cannot be null")
    private DroneStateEnum state;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MedicationEntity> payload = new ArrayList<>();

    public void addItem(MedicationEntity medication) {
        payload.add(medication);
        medication.setDrone(this); // maintain bidirectional relationship
    }

    public void removeItem(MedicationEntity medication) {
        payload.remove(medication);
        medication.setDrone(null); // cleanly break the relationship
    }
}
