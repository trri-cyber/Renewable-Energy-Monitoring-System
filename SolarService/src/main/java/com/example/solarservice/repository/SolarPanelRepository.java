package com.example.solarservice.repository;

import com.example.solarservice.entity.SolarPanel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolarPanelRepository extends JpaRepository<SolarPanel, Long> {

    Optional<SolarPanel> findByDeviceName(String deviceName);

    @Query("""
            SELECT COALESCE(SUM(s.generationUnits), 0)
            FROM SolarPanel s
            """)
    Double getTotalGeneratedEnergy();
}