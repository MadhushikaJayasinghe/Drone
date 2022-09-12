package com.drone.drone.component;

import com.drone.drone.dto.DroneBatteryPercentageDto;
import com.drone.drone.entity.HistoryLog;
import com.drone.drone.repository.HistoryLogRepository;
import com.drone.drone.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoryLogComponent {

    @Autowired
    private HistoryLogRepository historyLogRepository;

    @Autowired
    private DroneService droneService;

    @Scheduled(cron = "*/5 * * * *", zone = "GMT+0:00")
    public void updateDroneHistoryLogs() {
        List<DroneBatteryPercentageDto> droneBatteryPercentages = droneService.getDroneBatteryPercentage();
        HistoryLog historyLog;
        for (DroneBatteryPercentageDto droneBatteryPercentage: droneBatteryPercentages) {
            historyLog = new HistoryLog(droneBatteryPercentage.getDroneId(), droneBatteryPercentage.getBatteryPercentage());
            historyLogRepository.save(historyLog);
        }
    }
}
