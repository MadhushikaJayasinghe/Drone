package com.drone.drone.repository;

import com.drone.drone.entity.HistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoryLogRepository extends JpaRepository<HistoryLog, UUID> {
}
