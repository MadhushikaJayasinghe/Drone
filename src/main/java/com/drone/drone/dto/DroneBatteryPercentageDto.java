package com.drone.drone.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DroneBatteryPercentageDto {
    private UUID droneId;
    private int batteryPercentage;
}
