package com.example.energydistributionservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WindTurbineResponse {

    private Long turbineId;

    private String deviceName;

    private String location;

    private Double capacity;

    private Double windSpeed;

    private Double energyGenerated;

    private String status;

    private boolean maintenance;
}