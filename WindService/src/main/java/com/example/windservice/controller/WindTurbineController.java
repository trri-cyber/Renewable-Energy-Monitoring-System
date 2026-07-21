package com.example.windservice.controller;

import com.example.windservice.dto.GenerationRequest;
import com.example.windservice.dto.WindTurbineRequest;
import com.example.windservice.dto.WindTurbineResponse;
import com.example.windservice.service.WindTurbineService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public WindTurbineResponse registerWindTurbine(
            @Valid @RequestBody WindTurbineRequest request) {

        log.info("POST : Register Wind Turbine");

        return service.registerWindTurbine(request);
    }

    @GetMapping
    public List<WindTurbineResponse> getAllWindTurbines() {

        log.info("GET : Fetch All Wind Turbines");

        return service.getAllWindTurbines();
    }

    @GetMapping("/total-generated")
    public Double getTotalGeneratedEnergy() {

        return service.getTotalGeneratedEnergy();
    }

    @GetMapping("/{id}")
    public WindTurbineResponse getWindTurbineById(
            @PathVariable Long id) {

        log.info("GET : Fetch Wind Turbine {}", id);

        return service.getWindTurbineById(id);
    }

    @GetMapping("/location/{location}")
    public List<WindTurbineResponse> getByLocation(
            @PathVariable String location) {

        log.info("GET : Fetch Wind Turbines by Location {}", location);

        return service.getWindTurbinesByLocation(location);
    }

    @PutMapping("/{id}")
    public WindTurbineResponse updateWindTurbine(
            @PathVariable Long id,
            @Valid @RequestBody WindTurbineRequest request) {

        log.info("PUT : Update Wind Turbine {}", id);

        return service.updateWindTurbine(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteWindTurbine(
            @PathVariable Long id) {

        log.info("DELETE : Delete Wind Turbine {}", id);

        service.deleteWindTurbine(id);

        return "Wind Turbine Deleted Successfully";
    }

    @PutMapping("/{id}/generation")
    public WindTurbineResponse updateGeneration(
            @PathVariable Long id,
            @Valid @RequestBody GenerationRequest request) {

        return service.updateGeneration(id, request);
    }
}