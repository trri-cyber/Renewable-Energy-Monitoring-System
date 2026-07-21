    package com.example.energydistributionservice.service;
    import org.springframework.http.ResponseEntity;
    import com.example.energydistributionservice.dto.BatteryResponse;
    import com.example.energydistributionservice.dto.*;
    import com.example.energydistributionservice.dto.SolarPanelResponse;
    import com.example.energydistributionservice.dto.WindTurbineResponse;

    import java.time.LocalDate;
    import java.util.ArrayList;

    import com.example.energydistributionservice.entity.EnergyDistribution;
    import com.example.energydistributionservice.exception.DuplicateDistributionException;
    import com.example.energydistributionservice.exception.EnergyDistributionNotFoundException;
    import com.example.energydistributionservice.repository.EnergyDistributionRepository;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;

    import java.time.LocalDateTime;
    import java.util.List;

    @Service
    @Slf4j
    public class EnergyDistributionServiceImpl implements EnergyDistributionService {

        private final RestTemplate restTemplate;

        public EnergyDistributionServiceImpl(
                EnergyDistributionRepository repository,
                RestTemplate restTemplate) {

            this.repository = repository;
            this.restTemplate = restTemplate;
        }

        private final EnergyDistributionRepository repository;

        @Override
        public DistributionBalanceResponse balanceEnergy(DistributionBalanceRequest request) {

            return DistributionBalanceResponse.builder()
                    .distributionId(0L)
                    .destination(request.getDestination())
                    .allocatedUnits(request.getRequiredUnits())
                    .sourceUsed("SOLAR")
                    .status("COMPLETED")
                    .build();
        }



        @Override
        public EnergyDistributionResponse createDistribution(EnergyDistributionRequest request) {

            log.info("Creating Energy Distribution");

            repository.findBySourceTypeAndDestination(
                            request.getSourceType(),
                            request.getDestination())
                    .ifPresent(distribution -> {
                        throw new DuplicateDistributionException(
                                "Distribution already exists for this source and destination.");
                    });

            if ("SOLAR".equalsIgnoreCase(request.getSourceType())) {

                Double totalGenerated;

                try {
                    totalGenerated = restTemplate.getForObject(
                            "http://localhost:8081/solar-panels/total-generated",
                            Double.class);
                } catch (Exception ex) {
                    throw new RuntimeException("Solar Service is unavailable.");
                }

                if (totalGenerated == null) {
                    totalGenerated = 0.0;
                }

                Double totalDistributed = repository.getTotalSolarDistributed();

                if (totalDistributed == null) {
                    totalDistributed = 0.0;
                }

                double availableEnergy = totalGenerated - totalDistributed;

                if (availableEnergy < request.getUnitsDistributed()) {
                    throw new RuntimeException(
                            "Insufficient Solar Energy. Available : "
                                    + availableEnergy
                                    + ", Requested : "
                                    + request.getUnitsDistributed());
                }

            } else if ("WIND".equalsIgnoreCase(request.getSourceType())) {

                Double totalGenerated;

                try {
                    totalGenerated = restTemplate.getForObject(
                            "http://localhost:8082/wind-turbines/total-generated",
                            Double.class);
                } catch (Exception ex) {
                    throw new RuntimeException("Wind Service is unavailable.");
                }

                if (totalGenerated == null) {
                    totalGenerated = 0.0;
                }

                Double totalDistributed = repository.getTotalWindDistributed();

                if (totalDistributed == null) {
                    totalDistributed = 0.0;
                }

                double availableEnergy = totalGenerated - totalDistributed;

                if (availableEnergy < request.getUnitsDistributed()) {
                    throw new RuntimeException(
                            "Insufficient Wind Energy. Available : "
                                    + availableEnergy
                                    + ", Requested : "
                                    + request.getUnitsDistributed());
                }

            } else if ("BATTERY".equalsIgnoreCase(request.getSourceType())) {

                throw new UnsupportedOperationException(
                        "Battery distribution will be implemented separately.");

            } else {

                throw new IllegalArgumentException("Invalid Source Type.");
            }

            EnergyDistribution distribution = EnergyDistribution.builder()
                    .sourceType(request.getSourceType())
                    .destination(request.getDestination())
                    .unitsDistributed(request.getUnitsDistributed())
                    .status(request.getStatus())
                    .createdAt(LocalDateTime.now())
                    .build();

            EnergyDistribution savedDistribution = repository.save(distribution);

            log.info("Distribution Created Successfully with ID {}",
                    savedDistribution.getDistributionId());

            return mapToResponse(savedDistribution);
        }
        @Override
        public EnergyDistributionResponse getDistributionById(Long id) {

            log.info("Fetching Distribution {}", id);

            EnergyDistribution distribution = repository.findById(id)
                    .orElseThrow(() ->
                            new EnergyDistributionNotFoundException(
                                    "Distribution not found with ID : " + id));

            return mapToResponse(distribution);
        }


        @Override
        public List<EnergyDistributionResponse> getDistributionsByDestination(String destination) {

            log.info("Fetching Distributions for {}", destination);

            return repository.findByDestination(destination)
                    .stream()
                    .map(this::mapToResponse)
                    .toList();
        }

        @Override
        public List<EnergyDistributionResponse> getDistributionsBySourceType(String sourceType) {

            log.info("Fetching Distributions by Source {}", sourceType);

            return repository.findBySourceType(sourceType)
                    .stream()
                    .map(this::mapToResponse)
                    .toList();
        }

        @Override
        public EnergyDistributionResponse updateDistribution(Long id,
                                                             EnergyDistributionRequest request) {

            log.info("Updating Distribution {}", id);

            EnergyDistribution distribution = repository.findById(id)
                    .orElseThrow(() ->
                            new EnergyDistributionNotFoundException(
                                    "Distribution not found with ID : " + id));

            distribution.setSourceType(request.getSourceType());
            distribution.setDestination(request.getDestination());
            distribution.setUnitsDistributed(request.getUnitsDistributed());
            distribution.setStatus(request.getStatus());
            distribution.setUpdatedAt(LocalDateTime.now());

            EnergyDistribution updatedDistribution = repository.save(distribution);

            log.info("Distribution Updated Successfully");

            return mapToResponse(updatedDistribution);
        }

        @Override
        public void deleteDistribution(Long id) {

            log.info("Deleting Distribution {}", id);

            EnergyDistribution distribution = repository.findById(id)
                    .orElseThrow(() ->
                            new EnergyDistributionNotFoundException(
                                    "Distribution not found with ID : " + id));

            repository.delete(distribution);

            log.info("Distribution Deleted Successfully");
        }

        @Override
        public DailyReportResponse getDailyReport() {

            List<EnergyDistribution> distributions = repository.findAll();

            double totalUnits = distributions.stream()
                    .mapToDouble(EnergyDistribution::getUnitsDistributed)
                    .sum();

            double solarUnits = distributions.stream()
                    .filter(d -> "SOLAR".equalsIgnoreCase(d.getSourceType()))
                    .mapToDouble(EnergyDistribution::getUnitsDistributed)
                    .sum();

            double windUnits = distributions.stream()
                    .filter(d -> "WIND".equalsIgnoreCase(d.getSourceType()))
                    .mapToDouble(EnergyDistribution::getUnitsDistributed)
                    .sum();

            double batteryUnits = distributions.stream()
                    .filter(d -> "BATTERY".equalsIgnoreCase(d.getSourceType()))
                    .mapToDouble(EnergyDistribution::getUnitsDistributed)
                    .sum();

            return DailyReportResponse.builder()
                    .date(LocalDate.now().toString())
                    .totalDistributions((long) distributions.size())
                    .totalUnitsDistributed(totalUnits)
                    .solarUnits(solarUnits)
                    .windUnits(windUnits)
                    .batteryUnits(batteryUnits)
                    .build();
        }
        @Override
        public List<FaultResponse> getFaults() {

            return new ArrayList<>();
        }
        @Override
        public String storeExcess(ExcessEnergyRequest request) {

            log.info("Received Excess Energy : {}", request.getExcessUnits());

            ResponseEntity<BatteryResponse[]> response =
                    restTemplate.getForEntity(
                            "http://localhost:8083/batteries",
                            BatteryResponse[].class);

            BatteryResponse[] batteries = response.getBody();

            if (batteries == null || batteries.length == 0) {
                return "No batteries available.";
            }

            double remainingEnergy = request.getExcessUnits();

            StringBuilder message = new StringBuilder();

            for (BatteryResponse battery : batteries) {

                double availableSpace =
                        battery.getCapacity() - battery.getCurrentCharge();

                if (availableSpace <= 0) {
                    continue;
                }

                double unitsToStore = Math.min(remainingEnergy, availableSpace);

                StoreEnergyRequest storeRequest =
                        new StoreEnergyRequest(unitsToStore);

                BatteryResponse result =
                        restTemplate.postForObject(
                                "http://localhost:8083/batteries/"
                                        + battery.getBatteryId()
                                        + "/store",
                                storeRequest,
                                BatteryResponse.class);

                if (result != null) {
                    message.append("Battery ")
                            .append(battery.getBatteryId())
                            .append(" stored ")
                            .append(unitsToStore)
                            .append(" units.\n");
                }

                remainingEnergy -= unitsToStore;

                if (remainingEnergy <= 0) {
                    break;
                }
            }

            if (remainingEnergy > 0) {
                message.append("Remaining ")
                        .append(remainingEnergy)
                        .append(" units could not be stored because all batteries are full.");
            } else {
                message.append("All excess energy stored successfully.");
            }

            return message.toString();
        }
        @Override
        public List<EnergyDistributionResponse> getAllDistributions() {

            log.info("Fetching All Distributions");

            return repository.findAll()
                    .stream()
                    .map(this::mapToResponse)
                    .toList();
        }


        private EnergyDistributionResponse mapToResponse(EnergyDistribution distribution) {

            return EnergyDistributionResponse.builder()
                    .distributionId(distribution.getDistributionId())
                    .sourceType(distribution.getSourceType())
                    .destination(distribution.getDestination())
                    .unitsDistributed(distribution.getUnitsDistributed())
                    .status(distribution.getStatus())
                    .build();
        }
    }