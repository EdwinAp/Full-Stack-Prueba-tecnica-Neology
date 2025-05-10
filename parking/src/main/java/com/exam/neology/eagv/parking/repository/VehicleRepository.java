package com.exam.neology.eagv.parking.repository;

import com.exam.neology.eagv.parking.entity.Vehicle;
import com.exam.neology.eagv.parking.entity.reflection.VehicleInfoReflection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    @Query(value = """
            SELECT v.plate as plate, cv.name as name, cv.description as description, ccv.cost as cost
            FROM vehicles v
            INNER JOIN cat_vehicles cv ON cv.id = v.cat_vehicles_id
            INNER JOIN cat_costs_vehicle ccv ON ccv.cat_vehicle_id = cv.id
            LIMIT :pageSize OFFSET :offSet
            """, nativeQuery = true)
    List<VehicleInfoReflection> pageRecords(@Param("pageSize") Integer pageSize, @Param("offSet")Integer offSet);

    @Query(value = """
            SELECT v.plate as plate, cv.name as name, cv.description as description, ccv.cost as cost
            FROM vehicles v
            INNER JOIN cat_vehicles cv ON cv.id = v.cat_vehicles_id
            INNER JOIN cat_costs_vehicle ccv ON ccv.cat_vehicle_id = cv.id
            WHERE v.plate = :plate
            """, nativeQuery = true)
    VehicleInfoReflection getVehicleInfoGeneral(@Param("plate") String plate);

    @Query(value = """
            SELECT count(*) FROM vehicles
            """, nativeQuery = true)
    Long totalElements();

}
