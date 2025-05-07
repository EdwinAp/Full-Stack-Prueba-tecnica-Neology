package com.exam.neology.eagv.parking.repository;

import com.exam.neology.eagv.parking.entity.StayVehicle;
import com.exam.neology.eagv.parking.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StayVehicleRepository extends JpaRepository<StayVehicle, String> {

    Optional<StayVehicle> findTopByPlateOrderByInputDateDesc(Vehicle plate);

    @Query("""
            SELECT sv FROM StayVehicle sv 
            JOIN sv.plate v 
            JOIN v.catVehicle cv 
            WHERE cv.id = :catVehicleId 
            AND sv.inputDate >= :startDate
            """)
    List<StayVehicle> findByCatVehicleIdAndStartDate(
            @Param("catVehicleId") Integer catVehicleId,
            @Param("startDate") LocalDateTime startDate
    );

}
