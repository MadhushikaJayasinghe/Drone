package com.drone.drone.service;

import com.drone.drone.dao.MedicationDao;
import com.drone.drone.dto.MedicationDto;
import com.drone.drone.dto.ResponseDto;
import com.drone.drone.entity.Medication;
import com.drone.drone.enums.MedicationOrderStatus;
import com.drone.drone.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MedicationServiceImpl implements MedicationService{
    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired DroneService droneService;

    @Autowired
    private MedicationDao medicationDao;

    @Override
    public ResponseEntity getLoadedMedicationItemsByDroneId(UUID droneId) {
        int count = droneService.getCountByDroneId(droneId);
        if (count == 1){
            List<MedicationDto> medications = medicationDao.getMedicationsByDroneId(droneId.toString());
            return new ResponseEntity<>(medications, HttpStatus.OK);
        } else {
            ResponseDto responseDto = new ResponseDto(false, "drone id not found", 404);
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        }
    }
}
