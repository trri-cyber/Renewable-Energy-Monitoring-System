package com.example.batteryservice.service;

import com.example.batteryservice.dto.BatteryRequest;
import com.example.batteryservice.dto.BatteryResponse;
import com.example.batteryservice.dto.StoreEnergyRequest;

import java.util.List;

public interface BatteryService {

    BatteryResponse storeEnergy(Long batteryId,
                                StoreEnergyRequest request);

    BatteryResponse registerBattery(BatteryRequest request);

    List<BatteryResponse> getAllBatteries();

    BatteryResponse getBatteryById(Long id);

    List<BatteryResponse> getBatteriesByLocation(String location);

    BatteryResponse updateBattery(Long id, BatteryRequest request);

    void deleteBattery(Long id);
}