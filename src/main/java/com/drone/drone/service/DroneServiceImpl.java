package com.drone.drone.service;

import com.drone.drone.dao.DroneDao;
import com.drone.drone.dao.MedicationDao;
import com.drone.drone.dto.*;
import com.drone.drone.entity.Drone;
import com.drone.drone.enums.DroneModel;
import com.drone.drone.enums.DroneState;
import com.drone.drone.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DroneServiceImpl implements DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationDao medicationDao;

    @Autowired
    private DroneDao droneDao;

    @Override
    public ResponseEntity registerDrone(DroneRegistrationDto registrationDto) {
        int count = droneRepository.countBySerialNumber(registrationDto.getSerialNumber());
        if (count > 0) { // Checking the given serial umber is already existing in the system
            ResponseDto responseDto = new ResponseDto(false, "drone already existing", 400);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
        Drone drone = new Drone(registrationDto.getSerialNumber(), DroneModel.valueOf(registrationDto.getModel())
                , registrationDto.getWeightLimit(), registrationDto.getBatteryCapacity(), DroneState.valueOf(registrationDto.getDroneState()));
        Drone savedDrone = droneRepository.save(drone);//Save drone in drone table
        return new ResponseEntity<>(savedDrone, HttpStatus.OK);
    }

    @Override
    public ResponseEntity loadMedications(MedicationsLoadingDto loadingDto) {
        Drone drone = droneRepository.getByDroneId(loadingDto.getDroneId());
        if (drone == null) { //Validating drone ID
            ResponseDto responseDto = new ResponseDto(false, "drone id is not found", 404);
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        }
        if (drone.getBatteryCapacity() < 25) { // Checking drone battery percentage
            ResponseDto responseDto = new ResponseDto(false, "drone battery percentage is less than 25", 400);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        } else if (!(drone.getState().equals(DroneState.IDLE) || drone.getState().equals(DroneState.LOADING))) { //Checking drone state
            ResponseDto responseDto = new ResponseDto(false, "drone is not available for loading", 400);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
        int givenWeight = medicationDao.getOpenWeightByMedicationId(loadingDto.getMedicationsIds()); // Get weight of given medications to store on the drone
        if (drone.getState().equals(DroneState.LOADING)) {
            int weight = medicationDao.getLoadedWeightByDroneId(loadingDto.getDroneId().toString()); // Get already loaded weight in given drone
            if ((weight + givenWeight) > drone.getWeightLimit()) { //Validate whether weight is exceeding the maximum weight limit
                ResponseDto responseDto = new ResponseDto(false, "drone max weight limit is exceeding", 400);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        } else if (drone.getState().equals(DroneState.IDLE)) {
            if (givenWeight > drone.getWeightLimit()) { //Validate whether weight is exceeding the maximum weight limit
                ResponseDto responseDto = new ResponseDto(false, "drone max weight limit is exceeding", 400);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        }
        for (String medicationId : loadingDto.getMedicationsIds()) { //Update medication order state to LOADED and drone id
            medicationDao.updateStatus(medicationId, drone.getDroneId().toString());
        }
        drone.setState(DroneState.LOADING); //Update drone status to LOADING
        droneRepository.save(drone);
        ResponseDto responseDto = new ResponseDto(true, "successfully updated", 200);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity checkAvailableDronesForLoading() {
        List<DroneIdSerialDto> availableDronesForLoading = droneDao.getAvailableDronesForLoading();
        return new ResponseEntity<>(availableDronesForLoading, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getBatteryLevel(UUID droneId) {
        int count = getCountByDroneId(droneId);
        if (count == 1) { //Check whether drone id is valid
            DroneBatteryPercentageDto droneBatteryPercentageDto = droneDao.getBatteryPercentageById(droneId);
            return new ResponseEntity<>(droneBatteryPercentageDto, HttpStatus.OK);
        } else {
            ResponseDto responseDto = new ResponseDto(false, "drone id not found", 404);
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<DroneBatteryPercentageDto> getDroneBatteryPercentage() {
        return droneDao.getDroneBatteryPercentage();
    }

    @Override
    public int getCountByDroneId(UUID droneId) {
        return droneRepository.countByDroneId(droneId);
    }
}
