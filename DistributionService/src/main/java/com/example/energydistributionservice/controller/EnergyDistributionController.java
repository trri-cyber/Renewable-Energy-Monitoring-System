package com.example.energydistributionservice.controller;

import com.example.energydistributionservice.dto.*;
import com.example.energydistributionservice.service.EnergyDistributionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/distributions")
@Slf4j
public class EnergyDistributionController {

    private final EnergyDistributionService energyDistributionService;

    public EnergyDistributionController(EnergyDistributionService energyDistributionService) {
        this.energyDistributionService = energyDistributionService;
    }

    @PostMapping("/balance")
    public DistributionBalanceResponse balanceEnergy(
            @Valid @RequestBody DistributionBalanceRequest request) {

        return energyDistributionService.balanceEnergy(request);
    }

    @GetMapping("/faults")
    public List<FaultResponse> getFaults() {

        return energyDistributionService.getFaults();
    }

    @GetMapping("/reports/daily")
    public DailyReportResponse getDailyReport() {

        return energyDistributionService.getDailyReport();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnergyDistributionResponse createDistribution(
            @Valid @RequestBody EnergyDistributionRequest request) {

        log.info("POST : Create Energy Distribution");

        return energyDistributionService.createDistribution(request);
    }

    @PostMapping("/store-excess")
    public String storeExcess(
            @RequestBody ExcessEnergyRequest request) {

        return energyDistributionService.storeExcess(request);
    }

    @GetMapping
    public List<EnergyDistributionResponse> getAllDistributions() {

        log.info("GET : Fetch All Distributions");

        return energyDistributionService.getAllDistributions();
    }

    @GetMapping("/{id}")
    public EnergyDistributionResponse getDistributionById(
            @PathVariable Long id) {

        log.info("GET : Fetch Distribution {}", id);

        return energyDistributionService.getDistributionById(id);
    }

    @GetMapping("/destination/{destination}")
    public List<EnergyDistributionResponse> getByDestination(
            @PathVariable String destination) {

        log.info("GET : Fetch Distributions By Destination {}", destination);

        return energyDistributionService.getDistributionsByDestination(destination);
    }

    @GetMapping("/source/{sourceType}")
    public List<EnergyDistributionResponse> getBySourceType(
            @PathVariable String sourceType) {

        log.info("GET : Fetch Distributions By Source {}", sourceType);

        return energyDistributionService.getDistributionsBySourceType(sourceType);
    }

    @PutMapping("/{id}")
    public EnergyDistributionResponse updateDistribution(
            @PathVariable Long id,
            @Valid @RequestBody EnergyDistributionRequest request) {

        log.info("PUT : Update Distribution {}", id);

        return energyDistributionService.updateDistribution(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteDistribution(
            @PathVariable Long id) {

        log.info("DELETE : Delete Distribution {}", id);

        energyDistributionService.deleteDistribution(id);

        return "Energy Distribution Deleted Successfully";
    }
}