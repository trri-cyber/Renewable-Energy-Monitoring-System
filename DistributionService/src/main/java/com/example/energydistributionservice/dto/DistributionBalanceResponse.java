package com.example.energydistributionservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributionBalanceResponse {

    private Long distributionId;

    private String destination;

    private Double allocatedUnits;

    private String sourceUsed;

    private String status;
}