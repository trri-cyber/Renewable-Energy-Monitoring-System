package com.example.energydistributionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcessEnergyRequest {

    private String sourceType;

    private Long sourceId;

    private Double excessUnits;

}