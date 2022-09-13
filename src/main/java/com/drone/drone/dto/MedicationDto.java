package com.drone.drone.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MedicationDto {
    private UUID medicationId;
    private String name;
    private int weight;
    private String code;
    private String photoUrl;
}
