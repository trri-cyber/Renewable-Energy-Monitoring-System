package com.example.windservice.entity;

import com.example.windservice.enums.TurbineStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "wind_turbines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WindTurbine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long turbineId;

    @Column(nullable = false, unique = true)
    private String deviceName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double capacity;

    @Column(nullable = false)
    private Double windSpeed;

    @Column(nullable = false)
    private Double energyGenerated;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TurbineStatus status;

    @Column(nullable = false)
    private boolean maintenance;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}