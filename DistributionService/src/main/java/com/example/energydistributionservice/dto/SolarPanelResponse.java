package com.example.energydistributionservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolarPanelResponse {

    private Long panelId;

    private String deviceName;

    private String location;

    private Double capacity;

    private Double generationUnits;

    private String status;

    private boolean maintenance;
}