package com.example.energydistributionservice.dto;

import com.example.energydistributionservice.enums.DistributionStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyDistributionRequest {

    @NotBlank(message = "Source type is required")
    private String sourceType;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Units distributed is required")
    @Positive(message = "Units distributed must be greater than 0")
    private Double unitsDistributed;

    @NotNull(message = "Status is required")
    private DistributionStatus status;
}