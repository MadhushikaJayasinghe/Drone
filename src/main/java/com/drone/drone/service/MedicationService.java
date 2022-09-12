package com.drone.drone.service;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface MedicationService {
    ResponseEntity getLoadedMedicationItemsByDroneId(UUID droneId);
}
