package com.example.energydistributionservice.entity;

import com.example.energydistributionservice.enums.DistributionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "energy_distribution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyDistribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long distributionId;

    @Column(nullable = false)
    private String sourceType;
    

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private Double unitsDistributed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DistributionStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}