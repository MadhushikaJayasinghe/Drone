package com.drone.drone.dao;

import com.drone.drone.dto.DroneBatteryPercentageDto;
import com.drone.drone.dto.DroneIdSerialDto;

import java.util.List;
import java.util.UUID;

public interface DroneDao {
    List<DroneIdSerialDto> getAvailableDronesForLoading();
    DroneBatteryPercentageDto getBatteryPercentageById(UUID droneId);
    List<DroneBatteryPercentageDto> getDroneBatteryPercentage();
}
