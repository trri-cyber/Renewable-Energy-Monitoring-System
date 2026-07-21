package com.example.windservice.repository;

import com.example.windservice.entity.WindTurbine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WindTurbineRepository extends JpaRepository<WindTurbine, Long> {

    Optional<WindTurbine> findByDeviceName(String deviceName);

    @Query("""
       SELECT COALESCE(SUM(w.energyGenerated), 0)
       FROM WindTurbine w
       """)
    Double getTotalGeneratedEnergy();

    List<WindTurbine> findByLocation(String location);
}