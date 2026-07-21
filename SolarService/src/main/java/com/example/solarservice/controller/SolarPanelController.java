package com.example.solarservice.controller;
import com.example.solarservice.dto.GenerationRequest;
import com.example.solarservice.dto.SolarPanelRequest;
import com.example.solarservice.dto.SolarPanelResponse;
import com.example.solarservice.service.SolarPanelService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/solar-panels")
@RequiredArgsConstructor
@Slf4j
public class SolarPanelController {

    private final SolarPanelService solarPanelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolarPanelResponse registerSolarPanel(
            @Valid @RequestBody SolarPanelRequest request) {

        log.info("POST /solar-panels");

        return solarPanelService.registerSolarPanel(request);
    }

    @GetMapping
    public List<SolarPanelResponse> getAllSolarPanels() {

        log.info("GET /solar-panels");

        return solarPanelService.getAllSolarPanels();
    }

    @GetMapping("/{id}")
    public SolarPanelResponse getSolarPanelById(@PathVariable Long id) {

        log.info("GET /solar-panels/{}", id);

        return solarPanelService.getSolarPanelById(id);
    }

    @GetMapping("/total-generated")
    public Double getTotalGeneratedEnergy() {

        return solarPanelService.getTotalGeneratedEnergy();
    }

    @PutMapping("/{id}")
    public SolarPanelResponse updateSolarPanel(
            @PathVariable Long id,
            @Valid @RequestBody SolarPanelRequest request) {

        log.info("PUT /solar-panels/{}", id);

        return solarPanelService.updateSolarPanel(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteSolarPanel(@PathVariable Long id) {

        log.info("DELETE /solar-panels/{}", id);

        solarPanelService.deleteSolarPanel(id);

        return "Solar Panel Deleted Successfully";
    }

    @PutMapping("/{id}/generation")
    public SolarPanelResponse updateGeneration(
            @PathVariable Long id,
            @Valid @RequestBody GenerationRequest request) {

        return solarPanelService.updateGeneration(id, request);
    }
}