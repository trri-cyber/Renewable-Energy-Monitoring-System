package com.example.windservice.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GenerationRequest {

    @Min(value = 0, message = "Generated units cannot be negative")
    private Double generatedUnits;
}