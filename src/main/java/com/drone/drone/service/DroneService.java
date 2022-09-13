package com.drone.drone.service;

import com.drone.drone.dto.DroneBatteryPercentageDto;
import com.drone.drone.dto.DroneRegistrationDto;
import com.drone.drone.dto.MedicationsLoadingDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface DroneService {
    ResponseEntity registerDrone(DroneRegistrationDto registrationDto);

    ResponseEntity loadMedications(MedicationsLoadingDto loadingDto);

    ResponseEntity checkAvailableDronesForLoading();

    ResponseEntity getBatteryLevel(UUID droneId);

    List<DroneBatteryPercentageDto> getDroneBatteryPercentage();

    int getCountByDroneId(UUID droneId);
}
