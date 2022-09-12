package com.drone.drone.repository;

import com.drone.drone.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DroneRepository extends JpaRepository<Drone, UUID> {
    int countBySerialNumber(String serialNumber);
    Drone getByDroneId(UUID droneId);
    int countByDroneId(UUID droneId);

}
