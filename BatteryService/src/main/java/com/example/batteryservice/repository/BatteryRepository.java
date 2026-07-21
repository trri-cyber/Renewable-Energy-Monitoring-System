package com.example.batteryservice.repository;

import com.example.batteryservice.entity.Battery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BatteryRepository extends JpaRepository<Battery, Long> {

    Optional<Battery> findByDeviceName(String deviceName);

    List<Battery> findByLocation(String location);
}