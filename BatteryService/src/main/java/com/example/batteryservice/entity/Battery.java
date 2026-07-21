package com.example.batteryservice.entity;

import com.example.batteryservice.enums.BatteryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "battery_units")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batteryId;

    @Column(nullable = false, unique = true)
    private String deviceName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double capacity;

    @Column(nullable = false)
    private Double currentCharge;

    @Column(nullable = false)
    private Double chargingRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatteryStatus status;

    @Column(nullable = false)
    private boolean maintenance;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}