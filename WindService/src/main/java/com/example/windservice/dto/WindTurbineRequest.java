package com.example.windservice.dto;

import com.example.windservice.enums.TurbineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WindTurbineRequest {

    @NotBlank(message = "Device name is required")
    private String deviceName;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    private Double capacity;

    @NotNull(message = "Wind speed is required")
    @Positive(message = "Wind speed must be greater than 0")
    private Double windSpeed;

    @NotNull(message = "Energy generated is required")
    @Positive(message = "Energy generated must be greater than 0")
    private Double energyGenerated;

    @NotNull(message = "Status is required")
    private TurbineStatus status;

    private boolean maintenance;
}