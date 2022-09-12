package com.drone.drone.entity;

import com.drone.drone.enums.DroneModel;
import com.drone.drone.enums.DroneState;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Entity
public class Drone {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(columnDefinition = "char(36)")
    private UUID droneId;

    @Column( nullable = false) //check
    @Size(max = 100)
    private  String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DroneModel model;

    @Column(nullable = false) //Add max and min limit
    @Max(500)
    @Min(0)
    private double weightLimit;

    @Column(nullable = false) //Add max and min limit
    @Max(100)
    @Min(0)
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DroneState state;

    public Drone(@Size(max = 100) String serialNumber, DroneModel model, @Max(500) @Min(0) double weightLimit, @Max(100) @Min(0) int batteryCapacity, DroneState state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    public Drone() {
    }
}
