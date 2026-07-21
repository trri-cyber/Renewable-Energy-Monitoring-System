package com.example.batteryservice.dto;

import com.example.batteryservice.enums.BatteryStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatteryRequest {

    @NotBlank(message = "Device name is required")
    private String deviceName;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    private Double capacity;

    @NotNull(message = "Current charge is required")
    @Positive(message = "Current charge must be greater than 0")
    private Double currentCharge;

    @NotNull(message = "Charging rate is required")
    @Positive(message = "Charging rate must be greater than 0")
    private Double chargingRate;

    @NotNull(message = "Status is required")
    private BatteryStatus status;

    private boolean maintenance;
}