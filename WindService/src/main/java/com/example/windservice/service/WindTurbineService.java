package com.example.windservice.service;

import com.example.windservice.dto.GenerationRequest;
import com.example.windservice.dto.WindTurbineRequest;
import com.example.windservice.dto.WindTurbineResponse;

import java.util.List;

public interface WindTurbineService {

    Double getTotalGeneratedEnergy();

    WindTurbineResponse updateGeneration(Long id, GenerationRequest request);

    WindTurbineResponse registerWindTurbine(WindTurbineRequest request);

    List<WindTurbineResponse> getAllWindTurbines();

    WindTurbineResponse getWindTurbineById(Long id);

    List<WindTurbineResponse> getWindTurbinesByLocation(String location);

    WindTurbineResponse updateWindTurbine(Long id, WindTurbineRequest request);

    void deleteWindTurbine(Long id);
}