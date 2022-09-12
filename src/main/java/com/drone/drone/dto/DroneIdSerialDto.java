package com.drone.drone.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DroneIdSerialDto {
    private UUID droneId;
    private String serialNumber;
}
