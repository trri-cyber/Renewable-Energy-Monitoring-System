package com.example.solarservice.entity;
import com.example.solarservice.enums.PanelStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "solar_panels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolarPanel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long panelId;

    @Column(nullable = false, unique = true)
    private String deviceName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double capacity;

    @Column(nullable = false)
    private Double generationUnits;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PanelStatus status;

    @Column(nullable = false)
    private boolean maintenance;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    
}