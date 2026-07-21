package com.example.energydistributionservice.controller;

import com.example.energydistributionservice.dto.*;
import com.example.energydistributionservice.service.EnergyDistributionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DistributionBalanceResponse> balanceEnergy(
            @Valid @RequestBody DistributionBalanceRequest request) {

        return ResponseEntity.ok(
                energyDistributionService.balanceEnergy(request));
    }
    @GetMapping("/faults")
    public ResponseEntity<List<FaultResponse>> getFaults() {

        return ResponseEntity.ok(
                energyDistributionService.getFaults());
    }
    @GetMapping("/reports/daily")
    public ResponseEntity<DailyReportResponse> getDailyReport() {

        return ResponseEntity.ok(
                energyDistributionService.getDailyReport());
    }

    @PostMapping
    public ResponseEntity<EnergyDistributionResponse> createDistribution(
            @Valid @RequestBody EnergyDistributionRequest request) {

        log.info("POST : Create Energy Distribution");

        return new ResponseEntity<>(
                energyDistributionService.createDistribution(request),
                HttpStatus.CREATED);
    }
    @PostMapping("/store-excess")
    public ResponseEntity<String> storeExcess(
            @RequestBody ExcessEnergyRequest request) {

        return ResponseEntity.ok(
                energyDistributionService.storeExcess(request));
    }

    @GetMapping
    public ResponseEntity<List<EnergyDistributionResponse>> getAllDistributions() {

        log.info("GET : Fetch All Distributions");

        return ResponseEntity.ok(
                energyDistributionService.getAllDistributions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergyDistributionResponse> getDistributionById(
            @PathVariable Long id) {

        log.info("GET : Fetch Distribution {}", id);

        return ResponseEntity.ok(
                energyDistributionService.getDistributionById(id));
    }

    @GetMapping("/destination/{destination}")
    public ResponseEntity<List<EnergyDistributionResponse>> getByDestination(
            @PathVariable String destination) {

        log.info("GET : Fetch Distributions By Destination {}", destination);

        return ResponseEntity.ok(
                energyDistributionService.getDistributionsByDestination(destination));
    }

    @GetMapping("/source/{sourceType}")
    public ResponseEntity<List<EnergyDistributionResponse>> getBySourceType(
            @PathVariable String sourceType) {

        log.info("GET : Fetch Distributions By Source {}", sourceType);

        return ResponseEntity.ok(
                energyDistributionService.getDistributionsBySourceType(sourceType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnergyDistributionResponse> updateDistribution(
            @PathVariable Long id,
            @Valid @RequestBody EnergyDistributionRequest request) {

        log.info("PUT : Update Distribution {}", id);

        return ResponseEntity.ok(
                energyDistributionService.updateDistribution(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDistribution(
            @PathVariable Long id) {

        log.info("DELETE : Delete Distribution {}", id);

        energyDistributionService.deleteDistribution(id);

        return ResponseEntity.ok("Energy Distribution Deleted Successfully");
    }
}