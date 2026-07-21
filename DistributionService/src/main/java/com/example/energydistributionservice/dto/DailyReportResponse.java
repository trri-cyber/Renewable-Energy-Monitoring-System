package com.example.energydistributionservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyReportResponse {

    private String date;

    private Long totalDistributions;

    private Double totalUnitsDistributed;

    private Double solarUnits;

    private Double windUnits;

    private Double batteryUnits;
}