package com.example.energydistributionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributionBalanceRequest {

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Required units are required")
    @Positive(message = "Required units must be greater than 0")
    private Double requiredUnits;
}