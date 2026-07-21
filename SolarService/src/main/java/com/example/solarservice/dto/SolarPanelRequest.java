package com.example.solarservice.dto;
import jakarta.validation.constraints.NotNull;
import com.example.solarservice.enums.PanelStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolarPanelRequest {

    @NotBlank(message = "Device name is required")
    private String deviceName;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    private Double capacity;

    @NotNull(message = "Generation units are required")
    @Positive(message = "Generation units must be positive")
    private Double generationUnits;

    @NotNull(message = "Status is required")
    private PanelStatus status;

    private boolean maintenance;
}