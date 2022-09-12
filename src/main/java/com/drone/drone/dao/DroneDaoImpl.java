package com.drone.drone.dao;

import com.drone.drone.dto.DroneBatteryPercentageDto;
import com.drone.drone.dto.DroneIdSerialDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class DroneDaoImpl implements DroneDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DroneIdSerialDto> getAvailableDronesForLoading() {
        return jdbcTemplate.query("SELECT drone_id,serial_number FROM drone WHERE battery_capacity > 24 AND state IN ('IDLE' , 'LOADING')",
                new DroneIdSerialDtoRowMapper());
    }

    @Override
    public DroneBatteryPercentageDto getBatteryPercentageById(UUID droneId) {
        return jdbcTemplate.queryForObject("SELECT drone_id,battery_capacity FROM drone WHERE drone_id = ?",  new DroneBatteryPercentageDtoRowMapper(),droneId.toString());
    }

    @Override
    public List<DroneBatteryPercentageDto> getDroneBatteryPercentage() {
        return jdbcTemplate.query("SELECT drone_id, battery_capacity FROM drone", new DroneBatteryPercentageDtoRowMapper());
    }

    public static final class DroneIdSerialDtoRowMapper implements RowMapper<DroneIdSerialDto> {
        @Override
        public DroneIdSerialDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            DroneIdSerialDto droneIdSerialDto = new DroneIdSerialDto();
            droneIdSerialDto.setDroneId(UUID.fromString(rs.getString("drone_id")));
            droneIdSerialDto.setSerialNumber(rs.getString("serial_number"));
            return droneIdSerialDto;
        }
    }

    public static final class DroneBatteryPercentageDtoRowMapper implements RowMapper<DroneBatteryPercentageDto> {
        @Override
        public DroneBatteryPercentageDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            DroneBatteryPercentageDto batteryPercentageDto = new DroneBatteryPercentageDto();
            batteryPercentageDto.setDroneId(UUID.fromString(rs.getString("drone_id")));
            batteryPercentageDto.setBatteryPercentage((rs.getInt("battery_capacity")));
            return batteryPercentageDto;
        }
    }
}
