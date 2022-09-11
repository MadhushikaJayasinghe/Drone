package com.drone.drone.service;

import com.drone.drone.dto.DroneRegistrationDto;
import org.springframework.http.ResponseEntity;

public interface DroneService {
    ResponseEntity registerDrone(DroneRegistrationDto registrationDto);
}
