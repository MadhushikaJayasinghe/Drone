package com.drone.drone.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MedicationsLoadingDto {
    private UUID droneId;
    private List<String> medicationsIds;
}
