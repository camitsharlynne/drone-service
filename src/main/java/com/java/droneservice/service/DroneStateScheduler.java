package com.java.droneservice.service;

import com.java.droneservice.model.DroneStateEnum;
import com.java.droneservice.repository.DronesRepository;
import com.java.droneservice.repository.entities.DroneEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneStateScheduler {

    private final DronesRepository dronesRepository;

    public DroneStateScheduler(DronesRepository dronesRepository) {
        this.dronesRepository = dronesRepository;
    }

    /**
     * Every 10 minutes, get "LOADING" payloads and
     * set to "LOADED".
     */
    @Scheduled(fixedRate = 600000)
    public void fetchLoadingPayloads() {
        List<DroneEntity> payloadsInLoading = dronesRepository.findByState(DroneStateEnum.LOADING.getValue());

        payloadsInLoading.forEach(payload -> payload.setState(DroneStateEnum.LOADED));
        dronesRepository.saveAll(payloadsInLoading);
    }

    /**
     * Every 25 minutes, get "LOADED" payloads and
     * set to "DELIVERING".
     */
    @Scheduled(fixedRate = 1500000)
    public void fetchLoadedPayloads() {
        List<DroneEntity> payloadsLoaded = dronesRepository.findByState(DroneStateEnum.LOADED.getValue());

        payloadsLoaded.forEach(payload -> payload.setState(DroneStateEnum.DELIVERING));
        dronesRepository.saveAll(payloadsLoaded);
    }

    /**
     * Every 45 minutes, get "DELIVERING" payloads and
     * set to "DELIVERED". Trigger battery drain.
     */
    @Scheduled(fixedRate = 2700000)
    public void fetchDeliveringDrones() {
        List<DroneEntity> dronesDelivering = dronesRepository.findByState(DroneStateEnum.DELIVERING.getValue());

        dronesDelivering.forEach(payload -> {
            int battery = payload.getBatteryCapacity();
            payload.setState(DroneStateEnum.DELIVERED);

            if (battery >= 10) {
                payload.setBatteryCapacity(battery -= 10);
            }
        });
        dronesRepository.saveAll(dronesDelivering);
    }

    /**
     * Every 25 minutes, get "DELIVERED" payloads and
     * set to "RETURNING".
     */
    @Scheduled(fixedRate = 1500000)
    public void fetchDeliveredDrones() {
        List<DroneEntity> dronesDelivered = dronesRepository.findByState(DroneStateEnum.DELIVERED.getValue());

        dronesDelivered.forEach(payload -> payload.setState(DroneStateEnum.RETURNING));
        dronesRepository.saveAll(dronesDelivered);
    }

    /**
     * Every 25 minutes, get "RETURNED" payloads and
     * set to "IDLE".
     */
    @Scheduled(fixedRate = 1500000)
    public void fetchReturnedDrones() {
        List<DroneEntity> dronesReturned = dronesRepository.findByState(DroneStateEnum.RETURNING.getValue());

        dronesReturned.forEach(payload -> payload.setState(DroneStateEnum.IDLE));
        dronesRepository.saveAll(dronesReturned);
    }

}
