package com.example.solarservice.service;

import com.example.solarservice.dto.GenerationRequest;
import com.example.solarservice.dto.SolarPanelRequest;
import com.example.solarservice.dto.SolarPanelResponse;

import java.util.List;

public interface SolarPanelService {

    SolarPanelResponse updateGeneration(Long id, GenerationRequest request);

    SolarPanelResponse registerSolarPanel(SolarPanelRequest request);

    Double getTotalGeneratedEnergy();

    List<SolarPanelResponse> getAllSolarPanels();

    SolarPanelResponse getSolarPanelById(Long id);

    SolarPanelResponse updateSolarPanel(Long id, SolarPanelRequest request);

    void deleteSolarPanel(Long id);
}