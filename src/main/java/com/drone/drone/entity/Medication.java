package com.drone.drone.entity;

import com.drone.drone.enums.DroneModel;
import com.drone.drone.enums.MedicationOrderStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Medication {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(columnDefinition = "char(36)")
    private UUID medicationId;

    @Column( nullable = false)
    private  String name;

    @Column(nullable = false)
    private double weight;

    @Column(columnDefinition = "char(36)", nullable = false) //check
    private  String code;

    @Column( nullable = false)
    private String photoUrl;

    private UUID droneId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "default OPEN")
    private MedicationOrderStatus status;
}
