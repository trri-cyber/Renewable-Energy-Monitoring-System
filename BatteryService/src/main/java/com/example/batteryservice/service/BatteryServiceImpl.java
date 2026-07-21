package com.example.batteryservice.service;

import com.example.batteryservice.dto.BatteryRequest;
import com.example.batteryservice.dto.BatteryResponse;
import com.example.batteryservice.dto.StoreEnergyRequest;
import com.example.batteryservice.entity.Battery;
import com.example.batteryservice.exception.BatteryNotFoundException;
import com.example.batteryservice.exception.DuplicateBatteryException;
import com.example.batteryservice.repository.BatteryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BatteryServiceImpl implements BatteryService {

    private final BatteryRepository repository;

    public BatteryServiceImpl(BatteryRepository repository) {
        this.repository = repository;
    }

    @Override
    public BatteryResponse registerBattery(BatteryRequest request) {

        log.info("Registering Battery : {}", request.getDeviceName());

        if (repository.findByDeviceName(request.getDeviceName()).isPresent()) {
            throw new DuplicateBatteryException(
                    "Battery with device name '" + request.getDeviceName() + "' already exists.");
        }

        Battery battery = Battery.builder()
                .deviceName(request.getDeviceName())
                .location(request.getLocation())
                .capacity(request.getCapacity())
                .currentCharge(request.getCurrentCharge())
                .chargingRate(request.getChargingRate())
                .status(request.getStatus())
                .maintenance(request.isMaintenance())
                .createdAt(LocalDateTime.now())
                .build();

        Battery savedBattery = repository.save(battery);

        log.info("Battery registered successfully with ID {}", savedBattery.getBatteryId());

        return mapToResponse(savedBattery);
    }
    @Override
    public BatteryResponse storeEnergy(Long id,
                                       StoreEnergyRequest request) {

        log.info("Store Energy Request Received");
        log.info("Battery ID : {}", id);
        log.info("Units Received : {}", request.getUnits());

        Battery battery = repository.findById(id)
                .orElseThrow(() ->
                        new BatteryNotFoundException(
                                "Battery not found"));

        double availableSpace =
                battery.getCapacity() - battery.getCurrentCharge();

        log.info("Available Space : {}", availableSpace);

        double storedUnits =
                Math.min(request.getUnits(), availableSpace);

        double excessUnits =
                request.getUnits() - storedUnits;

        battery.setCurrentCharge(
                battery.getCurrentCharge() + storedUnits);

        Battery updatedBattery = repository.save(battery);

        log.info("Battery Updated. Current Charge : {}",
                updatedBattery.getCurrentCharge());

        BatteryResponse response = mapToResponse(updatedBattery);

        if (excessUnits > 0) {
            response.setMessage(excessUnits +
                    " units of excess energy could not be stored and were released to the ground.");
        } else {
            response.setMessage("All excess energy stored successfully.");
        }

        return response;
    }

    @Override
    public List<BatteryResponse> getAllBatteries() {

        log.info("Fetching all batteries");

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BatteryResponse getBatteryById(Long id) {

        log.info("Fetching Battery {}", id);

        Battery battery = repository.findById(id)
                .orElseThrow(() ->
                        new BatteryNotFoundException(
                                "Battery not found with ID : " + id));

        return mapToResponse(battery);
    }

    @Override
    public List<BatteryResponse> getBatteriesByLocation(String location) {

        log.info("Fetching Batteries in {}", location);

        return repository.findByLocation(location)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BatteryResponse updateBattery(Long id, BatteryRequest request) {

        log.info("Updating Battery {}", id);

        Battery battery = repository.findById(id)
                .orElseThrow(() ->
                        new BatteryNotFoundException(
                                "Battery not found with ID : " + id));

        if (!battery.getDeviceName().equals(request.getDeviceName())
                && repository.findByDeviceName(request.getDeviceName()).isPresent()) {

            throw new DuplicateBatteryException(
                    "Battery with device name '" + request.getDeviceName() + "' already exists.");
        }

        battery.setDeviceName(request.getDeviceName());
        battery.setLocation(request.getLocation());
        battery.setCapacity(request.getCapacity());
        battery.setCurrentCharge(request.getCurrentCharge());
        battery.setChargingRate(request.getChargingRate());
        battery.setStatus(request.getStatus());
        battery.setMaintenance(request.isMaintenance());
        battery.setUpdatedAt(LocalDateTime.now());

        Battery updatedBattery = repository.save(battery);

        log.info("Battery updated successfully");

        return mapToResponse(updatedBattery);
    }

    @Override
    public void deleteBattery(Long id) {

        log.info("Deleting Battery {}", id);

        Battery battery = repository.findById(id)
                .orElseThrow(() ->
                        new BatteryNotFoundException(
                                "Battery not found with ID : " + id));

        repository.delete(battery);

        log.info("Battery deleted successfully");
    }

    private BatteryResponse mapToResponse(Battery battery) {

        return BatteryResponse.builder()
                .batteryId(battery.getBatteryId())
                .deviceName(battery.getDeviceName())
                .location(battery.getLocation())
                .capacity(battery.getCapacity())
                .currentCharge(battery.getCurrentCharge())
                .chargingRate(battery.getChargingRate())
                .status(battery.getStatus())
                .maintenance(battery.isMaintenance())
                .build();
    }
}