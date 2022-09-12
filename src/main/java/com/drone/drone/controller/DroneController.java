package com.drone.drone.controller;

import com.drone.drone.dto.DroneRegistrationDto;
import com.drone.drone.dto.MedicationsLoadingDto;
import com.drone.drone.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Validated
@RestController
public class DroneController {

    @Autowired
    private DroneService droneService;

    @RequestMapping(value = "/drone/register", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity registerDrone(@Valid @RequestBody DroneRegistrationDto droneRegistrationDto) {
        return droneService.registerDrone(droneRegistrationDto);
    }

    @RequestMapping(value = "/drone/load-medications", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity loadMedications(@Valid @RequestBody MedicationsLoadingDto medicationsLoadingDto) {
        return droneService.loadMedications(medicationsLoadingDto);
    }

    @RequestMapping(value = "/drone/get-available-drones", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity getAvailableDronesForLoading() {
        return droneService.checkAvailableDronesForLoading();
    }

    @RequestMapping(value = "/drone/get-battery-capacity", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity getBatteryCapacity(@Valid @NotNull(message = "Drone id cannot be null")@RequestParam("droneId") UUID droneId) {
        return droneService.getBatteryLevel(droneId);
    }

}
