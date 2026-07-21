package com.example.windservice.controller;

import com.example.windservice.dto.GenerationRequest;
import com.example.windservice.dto.WindTurbineRequest;
import com.example.windservice.dto.WindTurbineResponse;
import com.example.windservice.service.WindTurbineService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wind-turbines")
@Slf4j
public class WindTurbineController {

    private final WindTurbineService service;

    public WindTurbineController(WindTurbineService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<WindTurbineResponse> registerWindTurbine(
            @Valid @RequestBody WindTurbineRequest request) {

        log.info("POST : Register Wind Turbine");

        return new ResponseEntity<>(
                service.registerWindTurbine(request),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WindTurbineResponse>> getAllWindTurbines() {

        log.info("GET : Fetch All Wind Turbines");

        return ResponseEntity.ok(service.getAllWindTurbines());
    }
    @GetMapping("/total-generated")
    public ResponseEntity<Double> getTotalGeneratedEnergy() {
        return ResponseEntity.ok(
                service.getTotalGeneratedEnergy());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WindTurbineResponse> getWindTurbineById(
            @PathVariable Long id) {

        log.info("GET : Fetch Wind Turbine {}", id);

        return ResponseEntity.ok(service.getWindTurbineById(id));
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<WindTurbineResponse>> getByLocation(
            @PathVariable String location) {

        log.info("GET : Fetch Wind Turbines by Location {}", location);

        return ResponseEntity.ok(service.getWindTurbinesByLocation(location));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WindTurbineResponse> updateWindTurbine(
            @PathVariable Long id,
            @Valid @RequestBody WindTurbineRequest request) {

        log.info("PUT : Update Wind Turbine {}", id);

        return ResponseEntity.ok(
                service.updateWindTurbine(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWindTurbine(
            @PathVariable Long id) {

        log.info("DELETE : Delete Wind Turbine {}", id);

        service.deleteWindTurbine(id);

        return ResponseEntity.ok("Wind Turbine Deleted Successfully");
    }
    @PutMapping("/{id}/generation")
    public ResponseEntity<WindTurbineResponse> updateGeneration(
            @PathVariable Long id,
            @RequestBody @Valid GenerationRequest request) {

        return ResponseEntity.ok(
                service.updateGeneration(id, request));
    }
}