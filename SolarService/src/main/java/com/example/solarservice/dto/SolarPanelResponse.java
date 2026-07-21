package com.example.solarservice.dto;

import com.example.solarservice.enums.PanelStatus;
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

    private PanelStatus status;

    private boolean maintenance;
}