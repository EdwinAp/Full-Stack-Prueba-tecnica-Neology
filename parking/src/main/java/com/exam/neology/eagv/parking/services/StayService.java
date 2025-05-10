package com.exam.neology.eagv.parking.services;

import com.exam.neology.eagv.parking.dtos.StayVehicleDto;
import com.exam.neology.eagv.parking.dtos.VehicleUpDto;
import com.exam.neology.eagv.parking.entity.StayVehicle;
import com.exam.neology.eagv.parking.entity.Vehicle;
import com.exam.neology.eagv.parking.repository.StayVehicleRepository;
import com.exam.neology.eagv.parking.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    public List<StayVehicleDto> obtainStayByPlate(String plate) {
        return stayVehicleRepository.findByPlate(plate).parallelStream()
                .map(stayVehicle -> StayVehicleDto.builder()
                        .inputDate(stayVehicle.getInputDate())
                        .outputDate(stayVehicle.getOutputDate())
                        .build())
                .toList();
    }

    public Boolean deleteCurrentMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        LocalDateTime startDate = LocalDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                1, 0, 0, 0 ,0);

        stayVehicleRepository.deleteByInputDateGreaterThanEqual(startDate);

        Integer counter = stayVehicleRepository.countCurrentMonth(startDate);

        return counter.equals(0);
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
