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
        if (count > 0) {
            ResponseDto responseDto = new ResponseDto(false, "drone already existing", 400);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
        Drone drone = new Drone(registrationDto.getSerialNumber(), DroneModel.valueOf(registrationDto.getModel())
                , registrationDto.getWeightLimit(), registrationDto.getBatteryCapacity(), DroneState.valueOf(registrationDto.getDroneState()));
        Drone savedDrone = droneRepository.save(drone);
        return new ResponseEntity<>(savedDrone, HttpStatus.OK);
    }

    @Override
    public ResponseEntity loadMedications(MedicationsLoadingDto loadingDto) {
        Drone drone = droneRepository.getByDroneId(loadingDto.getDroneId());
        if (drone.getBatteryCapacity() < 25) {
            ResponseDto responseDto = new ResponseDto(false, "drone battery percentage is less than 25", 400);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        } else if (!(drone.getDroneState().equals(DroneState.IDLE) || drone.getDroneState().equals(DroneState.LOADING))) {
            ResponseDto responseDto = new ResponseDto(false, "drone is not available for loading", 400);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
        double openWeight = medicationDao.getOpenWeightByMedicationId(loadingDto.getMedicationsIds());
        if (drone.getDroneState().equals(DroneState.LOADING)) {
            double weight = medicationDao.getLoadedWeightByDroneId(loadingDto.getDroneId().toString());
            if ((weight + openWeight) > drone.getWeightLimit()) {
                ResponseDto responseDto = new ResponseDto(false, "drone max weight limit is exceeding", 400);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

        } else if (drone.getDroneState().equals(DroneState.IDLE)){
            if (openWeight > drone.getWeightLimit()) {
                ResponseDto responseDto = new ResponseDto(false, "drone max weight limit is exceeding", 400);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        }
        medicationDao.updateStatus(loadingDto.getMedicationsIds());
        drone.setDroneState(DroneState.LOADING);
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
        DroneBatteryPercentageDto droneBatteryPercentageDto = droneDao.getBatteryPercentageById(droneId);
        return new ResponseEntity<>(droneBatteryPercentageDto, HttpStatus.OK);
    }


}
