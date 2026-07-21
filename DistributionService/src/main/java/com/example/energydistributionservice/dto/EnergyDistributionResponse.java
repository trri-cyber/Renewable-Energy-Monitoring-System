package com.example.energydistributionservice.dto;

import com.example.energydistributionservice.enums.DistributionStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyDistributionResponse {

    private Long distributionId;

    private String sourceType;
    

    private String destination;

    private Double unitsDistributed;

    private DistributionStatus status;
}