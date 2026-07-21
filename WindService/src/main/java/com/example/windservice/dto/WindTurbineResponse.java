package com.example.windservice.dto;

import com.example.windservice.enums.TurbineStatus;
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

    private TurbineStatus status;

    private boolean maintenance;
}