package com.drone.drone.controller;

import com.drone.drone.dto.DroneRegistrationDto;
import com.drone.drone.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Validated
@RestController
public class DroneController {

    @Autowired
    private DroneService droneService;

    @RequestMapping(value = "/drone/register", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity registerDrone(@Valid @RequestBody DroneRegistrationDto droneRegistrationDto) {
        return droneService.registerDrone(droneRegistrationDto);
    }
}
