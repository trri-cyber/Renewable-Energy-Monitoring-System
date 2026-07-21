package com.example.energydistributionservice.dto;

import lombok.Data;

@Data
public class BatteryResponse {

    private String message;
    private Long batteryId;
    private String deviceName;
    private String location;
    private Double capacity;
    private Double currentCharge;
    private Double chargingRate;
    private String status;
    private boolean maintenance;
}