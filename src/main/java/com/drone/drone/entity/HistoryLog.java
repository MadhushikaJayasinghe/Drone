package com.drone.drone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "HISTORY_LOG")
@EntityListeners(AuditingEntityListener.class) // to modify auto generating date data
@JsonIgnoreProperties(value ={"createdAt"},allowGetters = true)
public class HistoryLog {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID logId;

    @Type(type = "uuid-char")
    @Column(columnDefinition = "char(36)")
    private UUID droneId;

    @Column(nullable = false)
    @Max(100)
    @Min(0)
    private int batteryCapacity;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    @CreatedDate
    private Date createdAt;


    public HistoryLog(UUID droneId, @Max(100) @Min(0) int batteryCapacity) {
        this.droneId = droneId;
        this.batteryCapacity = batteryCapacity;
    }
}
