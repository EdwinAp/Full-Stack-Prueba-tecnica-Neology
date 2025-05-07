package com.exam.neology.eagv.parking.startup;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class StartupApplicationListener implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    public StartupApplicationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        insertInitialData();
    }

    private void insertInitialData() {
        // Insertar en CAT_VEHICLES
        jdbcTemplate.update(
                "MERGE INTO CAT_VEHICLES (ID, NAME, DESCRIPTION) KEY (ID) VALUES (1, 'oficial', 'Vehículo oficial')");
        jdbcTemplate.update(
                "MERGE INTO CAT_VEHICLES (ID, NAME, DESCRIPTION) KEY (ID) VALUES (2, 'residente', 'Vehículo de residente')");
        jdbcTemplate.update(
                "MERGE INTO CAT_VEHICLES (ID, NAME, DESCRIPTION) KEY (ID) VALUES (3, 'no_residente', 'Vehículo de no residente')");

        // Insertar en CAT_COST_VEHICLES
        jdbcTemplate.update(
                "MERGE INTO CAT_COSTS_VEHICLE (ID, CAT_VEHICLE_ID, COST) KEY (ID) VALUES (1, 1, 0.00)");
        jdbcTemplate.update(
                "MERGE INTO CAT_COSTS_VEHICLE (ID, CAT_VEHICLE_ID, COST) KEY (ID) VALUES (2, 2, 0.05)");
        jdbcTemplate.update(
                "MERGE INTO CAT_COSTS_VEHICLE (ID, CAT_VEHICLE_ID, COST) KEY (ID) VALUES (3, 3, 0.5)");
    }

}
