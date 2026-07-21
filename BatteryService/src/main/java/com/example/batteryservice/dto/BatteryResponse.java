package com.example.batteryservice.dto;

import com.example.batteryservice.enums.BatteryStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatteryResponse {

    private String message;

    private Long batteryId;

    private String deviceName;

    private String location;

    private Double capacity;

    private Double currentCharge;

    private Double chargingRate;

    private BatteryStatus status;

    private boolean maintenance;
}