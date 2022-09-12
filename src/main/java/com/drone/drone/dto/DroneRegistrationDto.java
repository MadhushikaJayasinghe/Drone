package com.drone.drone.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class DroneRegistrationDto {
    @Size (max = 100, message = "serial number must be less than 100 characters")
    private  String serialNumber;

    @Pattern(regexp = "Lightweight|Middleweight|Cruiserweight|Heavyweight", message = "invalid drone model")
    private String model;

    @Max(value = 500, message = "max weight limit is 500 grams")
    @Min(value = 0, message = "min weight limit is 0 grams")
    private int weightLimit;

    @Max(value = 100, message = "max battery capacity is 100")
    @Min(value = 0, message = "min battery capacity is 0")
    private int batteryCapacity;

    @Pattern(regexp = "IDLE|LOADING|LOADED|DELIVERING|DELIVERED|RETURNING", message = "invalid drone state")
    private String droneState;
}
