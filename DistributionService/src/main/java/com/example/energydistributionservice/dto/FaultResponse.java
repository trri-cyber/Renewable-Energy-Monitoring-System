package com.example.energydistributionservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultResponse {

    private String service;

    private String deviceName;

    private String status;
}