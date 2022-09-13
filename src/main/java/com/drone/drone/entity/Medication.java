package com.drone.drone.entity;

import com.drone.drone.enums.MedicationOrderStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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

    @Column(nullable = false)
    @Pattern(regexp = "([A-Za-z0-9\\-\\_]+)", message = "name must contain only letters numbers , - and _")
    private String name;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    @Pattern(regexp = "([A-Z0-9\\_]+)", message = "code must contain only uppercase letters numbers and _")
    private String code;

    @Column(nullable = false)
    private String photoUrl;

    private UUID droneId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedicationOrderStatus status;
}
