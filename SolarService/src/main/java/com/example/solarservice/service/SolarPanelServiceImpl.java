package com.example.solarservice.service;

import com.example.solarservice.dto.ExcessEnergyRequest;
import com.example.solarservice.dto.GenerationRequest;
import com.example.solarservice.dto.SolarPanelRequest;
import com.example.solarservice.dto.SolarPanelResponse;
import com.example.solarservice.entity.SolarPanel;
import com.example.solarservice.exception.DuplicateSolarPanelException;
import com.example.solarservice.exception.SolarPanelNotFoundException;
import com.example.solarservice.repository.SolarPanelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolarPanelServiceImpl implements SolarPanelService {

    private final SolarPanelRepository repository;
    private final RestTemplate restTemplate;


    @Override
    public SolarPanelResponse registerSolarPanel(SolarPanelRequest request) {

        log.info("Registering Solar Panel : {}", request.getDeviceName());

        if (repository.findByDeviceName(request.getDeviceName()).isPresent()) {
            throw new DuplicateSolarPanelException("Solar Panel already exists.");
        }

        SolarPanel panel = SolarPanel.builder()
                .deviceName(request.getDeviceName())
                .location(request.getLocation())
                .capacity(request.getCapacity())
                .generationUnits(request.getGenerationUnits())
                .status(request.getStatus())
                .maintenance(request.isMaintenance())
                .build();

        SolarPanel savedPanel = repository.save(panel);

        log.info("Solar Panel Registered Successfully with ID : {}", savedPanel.getPanelId());

        return mapToResponse(savedPanel);
    }
    @Override
    public SolarPanelResponse updateGeneration(Long id,
                                               GenerationRequest request) {

        SolarPanel panel = repository.findById(id)
                .orElseThrow(() ->
                        new SolarPanelNotFoundException(
                                "Solar Panel not found with ID : " + id));

        double totalGeneration =
                panel.getGenerationUnits() + request.getGeneratedUnits();

        if (totalGeneration > panel.getCapacity()) {

            double excess = totalGeneration - panel.getCapacity();

            panel.setGenerationUnits(panel.getCapacity());

            ExcessEnergyRequest excessRequest = new ExcessEnergyRequest(
                    "SOLAR",
                    panel.getPanelId(),
                    excess
            );

            String response = restTemplate.postForObject(
                    "http://localhost:8084/distributions/store-excess",
                    excessRequest,
                    String.class
            );

            log.info(response);

        } else {

            panel.setGenerationUnits(totalGeneration);
        }

        panel.setUpdatedAt(LocalDateTime.now());

        SolarPanel updatedPanel = repository.save(panel);

        return mapToResponse(updatedPanel);
    }

    @Override
    public Double getTotalGeneratedEnergy() {
        return repository.getTotalGeneratedEnergy();
    }


    @Override
    public List<SolarPanelResponse> getAllSolarPanels() {

        log.info("Fetching All Solar Panels");

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SolarPanelResponse getSolarPanelById(Long id) {

        log.info("Fetching Solar Panel with ID : {}", id);

        SolarPanel panel = repository.findById(id)
                .orElseThrow(() -> {

                    log.warn("Solar Panel Not Found with ID : {}", id);

                    return new SolarPanelNotFoundException(
                            "Solar Panel not found with ID : " + id);
                });

        return mapToResponse(panel);
    }

    @Override
    public SolarPanelResponse updateSolarPanel(Long id,
                                               SolarPanelRequest request) {

        log.info("Updating Solar Panel with ID : {}", id);

        SolarPanel panel = repository.findById(id)
                .orElseThrow(() -> {

                    log.warn("Solar Panel Not Found : {}", id);

                    return new SolarPanelNotFoundException(
                            "Solar Panel not found with ID : " + id);
                });

        panel.setDeviceName(request.getDeviceName());
        panel.setLocation(request.getLocation());
        panel.setCapacity(request.getCapacity());
        panel.setGenerationUnits(request.getGenerationUnits());
        panel.setStatus(request.getStatus());
        panel.setMaintenance(request.isMaintenance());

        SolarPanel updatedPanel = repository.save(panel);

        log.info("Solar Panel Updated Successfully : {}", id);

        return mapToResponse(updatedPanel);
    }

    @Override
    public void deleteSolarPanel(Long id) {

        log.info("Deleting Solar Panel : {}", id);

        SolarPanel panel = repository.findById(id)
                .orElseThrow(() -> {

                    log.warn("Solar Panel Not Found : {}", id);

                    return new SolarPanelNotFoundException(
                            "Solar Panel not found with ID : " + id);
                });

        repository.delete(panel);

        log.info("Solar Panel Deleted Successfully : {}", id);
    }

    private SolarPanelResponse mapToResponse(SolarPanel panel) {

        return SolarPanelResponse.builder()
                .panelId(panel.getPanelId())
                .deviceName(panel.getDeviceName())
                .location(panel.getLocation())
                .capacity(panel.getCapacity())
                .generationUnits(panel.getGenerationUnits())
                .status(panel.getStatus())
                .maintenance(panel.isMaintenance())
                .build();
    }
}