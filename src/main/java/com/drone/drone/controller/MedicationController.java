package com.drone.drone.controller;

import com.drone.drone.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @RequestMapping(value = "/medication/get-by-drone-id", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity getLoadedMedicationsByDroneId(@Valid @NotNull(message = "Drone id cannot be null") @RequestParam("droneId") UUID droneId) {
        return medicationService.getLoadedMedicationItemsByDroneId(droneId);
    }
}
