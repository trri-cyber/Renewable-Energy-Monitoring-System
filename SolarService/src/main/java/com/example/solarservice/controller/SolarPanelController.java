package com.example.solarservice.controller;

import com.example.solarservice.dto.GenerationRequest;
import com.example.solarservice.dto.SolarPanelRequest;
import com.example.solarservice.dto.SolarPanelResponse;
import com.example.solarservice.service.SolarPanelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solar-panels")
@RequiredArgsConstructor
@Slf4j
public class SolarPanelController {

    private final SolarPanelService solarPanelService;

    @PostMapping
    public ResponseEntity<SolarPanelResponse> registerSolarPanel(
            @Valid @RequestBody SolarPanelRequest request) {

        log.info("POST /solar-panels");

        SolarPanelResponse response =
                solarPanelService.registerSolarPanel(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SolarPanelResponse>> getAllSolarPanels() {

        log.info("GET /solar-panels");

        return ResponseEntity.ok(
                solarPanelService.getAllSolarPanels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolarPanelResponse> getSolarPanelById(
            @PathVariable Long id) {

        log.info("GET /solar-panels/{}", id);

        return ResponseEntity.ok(
                solarPanelService.getSolarPanelById(id));
    }
    @GetMapping("/total-generated")
    public ResponseEntity<Double> getTotalGeneratedEnergy() {
        return ResponseEntity.ok(
                solarPanelService.getTotalGeneratedEnergy());
    }


    @PutMapping("/{id}")
    public ResponseEntity<SolarPanelResponse> updateSolarPanel(
            @PathVariable Long id,
            @Valid @RequestBody SolarPanelRequest request) {

        log.info("PUT /solar-panels/{}", id);

        return ResponseEntity.ok(
                solarPanelService.updateSolarPanel(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSolarPanel(
            @PathVariable Long id) {

        log.info("DELETE /solar-panels/{}", id);

        solarPanelService.deleteSolarPanel(id);

        return ResponseEntity.ok("Solar Panel Deleted Successfully");
    }

   
    @PutMapping("/{id}/generation")
    public ResponseEntity<SolarPanelResponse> updateGeneration(
            @PathVariable Long id,
            @RequestBody @Valid GenerationRequest request) {

        return ResponseEntity.ok(
                solarPanelService.updateGeneration(id, request));
    }
}