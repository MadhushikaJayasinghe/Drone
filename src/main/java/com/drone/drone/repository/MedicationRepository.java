package com.drone.drone.repository;

import com.drone.drone.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, UUID> {
    List<Medication> getByDroneIdAndStatus(UUID droneId, String status);
}
