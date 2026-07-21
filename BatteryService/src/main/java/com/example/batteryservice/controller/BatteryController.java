package com.example.batteryservice.controller;

import com.example.batteryservice.dto.BatteryRequest;
import com.example.batteryservice.dto.BatteryResponse;
import com.example.batteryservice.dto.StoreEnergyRequest;
import com.example.batteryservice.service.BatteryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BatteryResponse> registerBattery(
            @Valid @RequestBody BatteryRequest request) {

        log.info("POST : Register Battery");

        return new ResponseEntity<>(
                batteryService.registerBattery(request),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BatteryResponse>> getAllBatteries() {

        log.info("GET : Fetch All Batteries");

        return ResponseEntity.ok(
                batteryService.getAllBatteries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatteryResponse> getBatteryById(
            @PathVariable Long id) {

        log.info("GET : Fetch Battery {}", id);

        return ResponseEntity.ok(
                batteryService.getBatteryById(id));
    }


    @GetMapping("/location/{location}")
    public ResponseEntity<List<BatteryResponse>> getBatteryByLocation(
            @PathVariable String location) {

        log.info("GET : Fetch Batteries By Location {}", location);

        return ResponseEntity.ok(
                batteryService.getBatteriesByLocation(location));
    }
    @PostMapping("/{id}/store")
    public ResponseEntity<BatteryResponse> storeEnergy(
            @PathVariable Long id,
            @RequestBody @Valid StoreEnergyRequest request) {

        return ResponseEntity.ok(
                batteryService.storeEnergy(id, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BatteryResponse> updateBattery(
            @PathVariable Long id,
            @Valid @RequestBody BatteryRequest request) {

        log.info("PUT : Update Battery {}", id);

        return ResponseEntity.ok(
                batteryService.updateBattery(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBattery(
            @PathVariable Long id) {

        log.info("DELETE : Delete Battery {}", id);

        batteryService.deleteBattery(id);

        return ResponseEntity.ok("Battery Deleted Successfully");
    }
}