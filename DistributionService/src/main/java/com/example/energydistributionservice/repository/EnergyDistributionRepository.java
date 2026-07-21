package com.example.energydistributionservice.repository;
import java.util.Optional;
import com.example.energydistributionservice.entity.EnergyDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnergyDistributionRepository extends JpaRepository<EnergyDistribution, Long> {


    Optional<EnergyDistribution> findBySourceTypeAndDestination(
            String sourceType,
            String destination);
    @Query("""
       SELECT COALESCE(SUM(e.unitsDistributed), 0)
       FROM EnergyDistribution e
       WHERE e.sourceType = 'SOLAR'
       AND e.status = 'COMPLETED'
       """)
    Double getTotalSolarDistributed();

    @Query("""
       SELECT COALESCE(SUM(e.unitsDistributed), 0)
       FROM EnergyDistribution e
       WHERE e.sourceType = 'WIND'
       AND e.status = 'COMPLETED'
       """)
    Double getTotalWindDistributed();
    List<EnergyDistribution> findByDestination(String destination);

    List<EnergyDistribution> findBySourceType(String sourceType);
}