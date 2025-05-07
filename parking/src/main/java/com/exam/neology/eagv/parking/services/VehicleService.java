package com.exam.neology.eagv.parking.services;

import com.exam.neology.eagv.parking.dtos.CatVehicleDto;
import com.exam.neology.eagv.parking.dtos.VehicleUpDto;
import com.exam.neology.eagv.parking.entity.CatVehicle;
import com.exam.neology.eagv.parking.entity.Vehicle;
import com.exam.neology.eagv.parking.repository.CatVehicleRepository;
import com.exam.neology.eagv.parking.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CatVehicleRepository catVehicleRepository;

    public VehicleService(
            VehicleRepository vehicleRepository,
            CatVehicleRepository catVehicleRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.catVehicleRepository = catVehicleRepository;
    }

    public List<CatVehicleDto> getAllCatVehicle() {
        return catVehicleRepository.findAll()
                .parallelStream()
                .map(catVehicle -> CatVehicleDto.builder()
                        .id(catVehicle.getId())
                        .name(catVehicle.getName())
                        .description(catVehicle.getDescription())
                        .build())
                .toList();
    }

    public VehicleUpDto upVehicle(VehicleUpDto vehicleUpDto) {

        Optional<CatVehicle> catVehicle = catVehicleRepository.findById(vehicleUpDto.getCatVehicle());

        Vehicle newVehicle = Vehicle.builder()
                .plate(vehicleUpDto.getPlate())
                .catVehicle(catVehicle.get())
                .build();

        vehicleRepository.save(newVehicle);

        return vehicleUpDto;
    }


}
