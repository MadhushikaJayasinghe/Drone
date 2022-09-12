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

    @Scheduled(cron = "0 * * * * *") //the method scheduled to run in every 5 minutes
    public void updateDroneHistoryLogs() {
        List<DroneBatteryPercentageDto> droneBatteryPercentages = droneService.getDroneBatteryPercentage(); //Getting all drone items in drone table
        HistoryLog historyLog;
        for (DroneBatteryPercentageDto droneBatteryPercentage: droneBatteryPercentages) {
            historyLog = new HistoryLog(droneBatteryPercentage.getDroneId(), droneBatteryPercentage.getBatteryPercentage());
            historyLogRepository.save(historyLog); //Save current drone battery capacity in history log
        }
    }
}
