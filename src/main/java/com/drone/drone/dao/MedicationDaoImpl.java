package com.drone.drone.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class MedicationDaoImpl implements MedicationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public double getLoadedWeightByDroneId(String droneId) throws NullPointerException{
        return jdbcTemplate.queryForObject("SELECT SUM (weight) FROM medication WHERE drone_id = ? AND status = LOADING", Double.class, new Object[]{droneId});
    }

    @Override
    public double getOpenWeightByMedicationId(List<String> medications) throws NullPointerException{
        Map idsMap = Collections.singletonMap("ids", medications);
        return namedParameterJdbcTemplate.queryForObject( "SELECT SUM (weight) FROM medication WHERE medication_id IN (:ids)", idsMap, Double.class);
    }

    @Override
    public void updateStatus(List<String> medications) {
        Map idsMap = Collections.singletonMap("ids", medications);
        namedParameterJdbcTemplate.update( "UPDATE medication SET status = LOADED WHERE medication_id IN (:ids)", idsMap);
    }

}
