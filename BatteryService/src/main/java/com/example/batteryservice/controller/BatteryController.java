package com.example.batteryservice.controller;

import com.example.batteryservice.dto.BatteryRequest;
import com.example.batteryservice.dto.BatteryResponse;
import com.example.batteryservice.dto.StoreEnergyRequest;
import com.example.batteryservice.service.BatteryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batteries")
@Slf4j
public class BatteryController {

    private final BatteryService batteryService;

    public BatteryController(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BatteryResponse registerBattery(
            @Valid @RequestBody BatteryRequest request) {

        log.info("POST : Register Battery");

        return batteryService.registerBattery(request);
    }

    @GetMapping
    public List<BatteryResponse> getAllBatteries() {

        log.info("GET : Fetch All Batteries");

        return batteryService.getAllBatteries();
    }

    @GetMapping("/{id}")
    public BatteryResponse getBatteryById(
            @PathVariable Long id) {

        log.info("GET : Fetch Battery {}", id);

        return batteryService.getBatteryById(id);
    }

    @GetMapping("/location/{location}")
    public List<BatteryResponse> getBatteryByLocation(
            @PathVariable String location) {

        log.info("GET : Fetch Batteries By Location {}", location);

        return batteryService.getBatteriesByLocation(location);
    }

    @PostMapping("/{id}/store")
    public BatteryResponse storeEnergy(
            @PathVariable Long id,
            @Valid @RequestBody StoreEnergyRequest request) {

        return batteryService.storeEnergy(id, request);
    }

    @PutMapping("/{id}")
    public BatteryResponse updateBattery(
            @PathVariable Long id,
            @Valid @RequestBody BatteryRequest request) {

        log.info("PUT : Update Battery {}", id);

        return batteryService.updateBattery(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteBattery(
            @PathVariable Long id) {

        log.info("DELETE : Delete Battery {}", id);

        batteryService.deleteBattery(id);

        return "Battery Deleted Successfully";
    }
}