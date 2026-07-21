package com.example.windservice.service;

import com.example.windservice.dto.GenerationRequest;
import com.example.windservice.dto.WindTurbineRequest;
import com.example.windservice.dto.WindTurbineResponse;
import com.example.windservice.entity.WindTurbine;
import com.example.windservice.exception.DuplicateWindTurbineException;
import com.example.windservice.exception.WindTurbineNotFoundException;
import com.example.windservice.repository.WindTurbineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.windservice.dto.ExcessEnergyRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class WindTurbineServiceImpl implements WindTurbineService {

    private final WindTurbineRepository repository;
    private final RestTemplate restTemplate;

    public WindTurbineServiceImpl(WindTurbineRepository repository,
                                  RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Override
    public WindTurbineResponse updateGeneration(Long id,
                                                GenerationRequest request) {

        log.info("Updating energy generation for wind turbine {}", id);

        WindTurbine turbine = repository.findById(id)
                .orElseThrow(() ->
                        new WindTurbineNotFoundException(
                                "Wind Turbine not found with ID : " + id));

        double totalEnergy =
                turbine.getEnergyGenerated() + request.getGeneratedUnits();

        if (totalEnergy > turbine.getCapacity()) {

            double excess = totalEnergy - turbine.getCapacity();

            turbine.setEnergyGenerated(turbine.getCapacity());

            ExcessEnergyRequest excessRequest =
                    new ExcessEnergyRequest(
                            "WIND",
                            turbine.getTurbineId(),
                            excess);

            String response = restTemplate.postForObject(
                    "http://localhost:8084/distributions/store-excess",
                    excessRequest,
                    String.class);

            log.info("Excess Energy Sent : {}", excess);
            log.info("Distribution Response : {}", response);

        } else {

            turbine.setEnergyGenerated(totalEnergy);
        }

        turbine.setUpdatedAt(LocalDateTime.now());

        WindTurbine updatedTurbine = repository.save(turbine);

        log.info("Energy generation updated successfully for wind turbine {}", id);

        return mapToResponse(updatedTurbine);
    }

    @Override
    public WindTurbineResponse registerWindTurbine(WindTurbineRequest request) {

        log.info("Registering Wind Turbine : {}", request.getDeviceName());

        if (repository.findByDeviceName(request.getDeviceName()).isPresent()) {
            throw new DuplicateWindTurbineException(
                    "Wind Turbine with device name '" + request.getDeviceName() + "' already exists.");
        }

        WindTurbine turbine = WindTurbine.builder()
                .deviceName(request.getDeviceName())
                .location(request.getLocation())
                .capacity(request.getCapacity())
                .windSpeed(request.getWindSpeed())
                .energyGenerated(request.getEnergyGenerated())
                .status(request.getStatus())
                .maintenance(request.isMaintenance())
                .createdAt(LocalDateTime.now())
                .build();

        WindTurbine saved = repository.save(turbine);

        log.info("Wind Turbine registered successfully with ID {}", saved.getTurbineId());

        return mapToResponse(saved);
    }

    @Override
    public List<WindTurbineResponse> getAllWindTurbines() {

        log.info("Fetching all wind turbines");

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public WindTurbineResponse getWindTurbineById(Long id) {

        log.info("Fetching wind turbine {}", id);

        WindTurbine turbine = repository.findById(id)
                .orElseThrow(() ->
                        new WindTurbineNotFoundException(
                                "Wind Turbine not found with ID : " + id));

        return mapToResponse(turbine);
    }

    @Override
    public List<WindTurbineResponse> getWindTurbinesByLocation(String location) {

        log.info("Fetching wind turbines at {}", location);

        return repository.findByLocation(location)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public WindTurbineResponse updateWindTurbine(Long id, WindTurbineRequest request) {

        log.info("Updating wind turbine {}", id);

        WindTurbine turbine = repository.findById(id)
                .orElseThrow(() ->
                        new WindTurbineNotFoundException(
                                "Wind Turbine not found with ID : " + id));

        if (!turbine.getDeviceName().equals(request.getDeviceName())
                && repository.findByDeviceName(request.getDeviceName()).isPresent()) {

            throw new DuplicateWindTurbineException(
                    "Wind Turbine with device name '" + request.getDeviceName() + "' already exists.");
        }

        turbine.setDeviceName(request.getDeviceName());
        turbine.setLocation(request.getLocation());
        turbine.setCapacity(request.getCapacity());
        turbine.setWindSpeed(request.getWindSpeed());
        turbine.setEnergyGenerated(request.getEnergyGenerated());
        turbine.setStatus(request.getStatus());
        turbine.setMaintenance(request.isMaintenance());
        turbine.setUpdatedAt(LocalDateTime.now());

        WindTurbine updated = repository.save(turbine);

        log.info("Wind turbine updated successfully");

        return mapToResponse(updated);
    }
    @Override
    public Double getTotalGeneratedEnergy() {
        return repository.getTotalGeneratedEnergy();
    }

    @Override
    public void deleteWindTurbine(Long id) {

        log.info("Deleting wind turbine {}", id);

        WindTurbine turbine = repository.findById(id)
                .orElseThrow(() ->
                        new WindTurbineNotFoundException(
                                "Wind Turbine not found with ID : " + id));

        repository.delete(turbine);

        log.info("Wind turbine deleted successfully");
    }

    private WindTurbineResponse mapToResponse(WindTurbine turbine) {

        return WindTurbineResponse.builder()
                .turbineId(turbine.getTurbineId())
                .deviceName(turbine.getDeviceName())
                .location(turbine.getLocation())
                .capacity(turbine.getCapacity())
                .windSpeed(turbine.getWindSpeed())
                .energyGenerated(turbine.getEnergyGenerated())
                .status(turbine.getStatus())
                .maintenance(turbine.isMaintenance())
                .build();
    }
}