package com.exam.neology.eagv.parking.repository;

import com.exam.neology.eagv.parking.entity.CatVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatVehicleRepository extends JpaRepository<CatVehicle, Integer> {
}
