package com.drone.drone.service;

import com.drone.drone.dto.DroneRegistrationDto;
import com.drone.drone.dto.MedicationsLoadingDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface DroneService {
    ResponseEntity registerDrone(DroneRegistrationDto registrationDto);
    ResponseEntity loadMedications(MedicationsLoadingDto loadingDto);
    ResponseEntity checkAvailableDronesForLoading();
    ResponseEntity getBatteryLevel(UUID droneId);

}
