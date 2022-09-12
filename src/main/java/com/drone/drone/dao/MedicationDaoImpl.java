package com.drone.drone.dao;

import com.drone.drone.dto.DroneIdSerialDto;
import com.drone.drone.dto.MedicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class MedicationDaoImpl implements MedicationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public double getLoadedWeightByDroneId(String droneId) throws NullPointerException{
        return jdbcTemplate.queryForObject("SELECT SUM (weight) FROM medication WHERE drone_id = ? AND status = 'LOADED'", Double.class, new Object[]{droneId});
    }

    @Override
    public double getOpenWeightByMedicationId(List<String> medications) throws NullPointerException{
        Map idsMap = Collections.singletonMap("ids", medications);
        return namedParameterJdbcTemplate.queryForObject( "SELECT SUM (weight) FROM medication WHERE medication_id IN (:ids)", idsMap, Double.class);
    }

    @Override
    public void updateStatus(String medicationId, String droneId) {
        namedParameterJdbcTemplate.update( "UPDATE medication SET status = 'LOADED' , drone_id = :droneId WHERE medication_id = :medicationId", new MapSqlParameterSource()
                .addValue("droneId", droneId)
                .addValue("medicationId", medicationId));
    }

    @Override
    public List<MedicationDto> getMedicationsByDroneId(String droneId) {
        return jdbcTemplate.query("SELECT medication_id,code,name,photo_url,weight FROM medication WHERE drone_id = ? AND status = 'LOADED'",
                new MedicationDtoRowMapper(),new Object[]{droneId});
    }

    public static final class MedicationDtoRowMapper implements RowMapper<MedicationDto> {
        @Override
        public MedicationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            MedicationDto medicationDto = new MedicationDto();
            medicationDto.setMedicationId(UUID.fromString(rs.getString("medication_id")));
            medicationDto.setCode(rs.getString("code"));
            medicationDto.setName(rs.getString("name"));
            medicationDto.setPhotoUrl(rs.getString("photo_url"));
            medicationDto.setWeight(rs.getInt("weight"));
            return medicationDto;
        }
    }

}
