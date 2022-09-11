package com.drone.drone.service;

import com.drone.drone.dto.DroneRegistrationDto;
import com.drone.drone.dto.ResponseDto;
import com.drone.drone.entity.Drone;
import com.drone.drone.enums.DroneModel;
import com.drone.drone.enums.DroneState;
import com.drone.drone.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DroneServiceImpl implements DroneService{

    @Autowired
    private DroneRepository droneRepository;

    @Override
    public ResponseEntity registerDrone(DroneRegistrationDto registrationDto) {
        int count = droneRepository.countBySerialNumber(registrationDto.getSerialNumber());
        if (count>0) {
            ResponseDto responseDto = new ResponseDto(false, "drone already existing", 400);
            return new  ResponseEntity<> (responseDto, HttpStatus.BAD_REQUEST);
        }
        Drone drone = new Drone(registrationDto.getSerialNumber(), DroneModel.valueOf(registrationDto.getModel())
        ,registrationDto.getWeightLimit(), registrationDto.getBatteryCapacity(), DroneState.valueOf(registrationDto.getDroneState()));
         Drone savedDrone = droneRepository.save(drone);
         return new ResponseEntity<>(savedDrone, HttpStatus.OK);
    }
}
