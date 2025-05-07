package com.exam.neology.eagv.parking.repository;

import com.exam.neology.eagv.parking.entity.CatCostVehicle;
import com.exam.neology.eagv.parking.entity.CatVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatCostVehicleRepository extends JpaRepository<CatCostVehicle, Integer> {

    Optional<CatCostVehicle> findByCatVehicle(CatVehicle catVehicle);

}
