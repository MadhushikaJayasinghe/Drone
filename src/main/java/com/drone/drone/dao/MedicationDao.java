package com.drone.drone.dao;

import com.drone.drone.dto.MedicationDto;

import java.util.List;

public interface MedicationDao {
    int getLoadedWeightByDroneId(String droneId);

    int getOpenWeightByMedicationId(List<String> medications);

    void updateStatus(String medicationId, String droneId);

    List<MedicationDto> getMedicationsByDroneId(String droneId);
}
