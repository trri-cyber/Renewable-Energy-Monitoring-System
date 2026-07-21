package com.example.energydistributionservice.service;

import com.example.energydistributionservice.dto.*;

import java.util.List;

public interface EnergyDistributionService {

    String storeExcess(ExcessEnergyRequest request);

    EnergyDistributionResponse createDistribution(EnergyDistributionRequest request);

    List<EnergyDistributionResponse> getAllDistributions();

    EnergyDistributionResponse getDistributionById(Long id);

    List<EnergyDistributionResponse> getDistributionsByDestination(String destination);

    List<EnergyDistributionResponse> getDistributionsBySourceType(String sourceType);

    EnergyDistributionResponse updateDistribution(Long id, EnergyDistributionRequest request);

    void deleteDistribution(Long id);

    DistributionBalanceResponse balanceEnergy(
            DistributionBalanceRequest request);

    DailyReportResponse getDailyReport();

    List<FaultResponse> getFaults();
}