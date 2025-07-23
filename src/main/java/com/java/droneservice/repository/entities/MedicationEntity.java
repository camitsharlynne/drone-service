package com.java.droneservice.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MEDICATION")
public class MedicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MEDICATION_NAME", nullable = false)
    @Pattern(regexp = "^[A-Za-z0-9_-]+$",
            message = "Only letters, numbers, -, _ are allowed")
    @NotNull(message = "Medication name cannot be null")
    private String medicationName;

    @Column(name = "WEIGHT", nullable = false)
    @NotNull(message = "Weight cannot be null")
    private int weight;

    @Column(name = "CODE", nullable = false)
    @NotNull(message = "Code cannot be null")
    @Pattern(regexp = "^[A-Z0-9_]+$",
            message = "Only uppercase letters, underscores and numbers are allowed")
    private String code;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "IMAGE", columnDefinition = "BLOB")
    @NotNull(message = "Image cannot be null")
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DRONE_ID")
    private DroneEntity drone;

}
