package com.drone.drone.dao;

import java.util.List;

public interface MedicationDao {
    double getLoadedWeightByDroneId (String droneId);
    double getOpenWeightByMedicationId(List<String> medications);
    void updateStatus(List<String> medications);
}
