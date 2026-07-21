package com.example.batteryservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StoreEnergyRequest {

    @NotNull(message = "Units are required")
    @DecimalMin(value = "0.1", message = "Units must be greater than 0")
    private Double units;
}