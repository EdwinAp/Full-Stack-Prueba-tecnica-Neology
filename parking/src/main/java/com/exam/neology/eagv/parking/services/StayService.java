package com.exam.neology.eagv.parking.services;

import com.exam.neology.eagv.parking.dtos.VehicleUpDto;
import com.exam.neology.eagv.parking.entity.StayVehicle;
import com.exam.neology.eagv.parking.entity.Vehicle;
import com.exam.neology.eagv.parking.repository.StayVehicleRepository;
import com.exam.neology.eagv.parking.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StayService {

    private final StayVehicleRepository stayVehicleRepository;
    private final VehicleRepository vehicleRepository;

    public StayService(
            StayVehicleRepository stayVehicleRepository,
            VehicleRepository vehicleRepository
    ) {
        this.stayVehicleRepository = stayVehicleRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleUpDto inputVehicle(VehicleUpDto vehicleUpDto) {

        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleUpDto.getPlate());

        StayVehicle stayVehicle = StayVehicle.builder()
                .inputDate(LocalDateTime.now())
                .plate(vehicle.get())
                .build();

        stayVehicleRepository.save(stayVehicle);

        return vehicleUpDto;
    }

    public VehicleUpDto outputVehicle(VehicleUpDto vehicleUpDto) {

        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleUpDto.getPlate());

        Optional<StayVehicle> stayVehicle = stayVehicleRepository.findTopByPlateOrderByInputDateDesc(vehicle.get());

        stayVehicle.get().setOutputDate(LocalDateTime.now());

        stayVehicleRepository.save(stayVehicle.get());

        return vehicleUpDto;
    }

}
