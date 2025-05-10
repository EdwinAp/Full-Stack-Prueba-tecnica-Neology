package com.exam.neology.eagv.parking.services;

import com.exam.neology.eagv.parking.dtos.CatVehicleDto;
import com.exam.neology.eagv.parking.dtos.PageResponse;
import com.exam.neology.eagv.parking.dtos.VehicleUpDto;
import com.exam.neology.eagv.parking.entity.CatVehicle;
import com.exam.neology.eagv.parking.entity.StayVehicle;
import com.exam.neology.eagv.parking.entity.Vehicle;
import com.exam.neology.eagv.parking.entity.VehicleDetailsDto;
import com.exam.neology.eagv.parking.entity.reflection.VehicleInfoReflection;
import com.exam.neology.eagv.parking.repository.CatVehicleRepository;
import com.exam.neology.eagv.parking.repository.StayVehicleRepository;
import com.exam.neology.eagv.parking.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CatVehicleRepository catVehicleRepository;
    private final StayVehicleRepository stayVehicleRepository;

    public VehicleService(
            VehicleRepository vehicleRepository,
            CatVehicleRepository catVehicleRepository,
            StayVehicleRepository stayVehicleRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.catVehicleRepository = catVehicleRepository;
        this.stayVehicleRepository = stayVehicleRepository;
    }

    public VehicleDetailsDto getVehicleInfoGeneral(String plate) {
        List<StayVehicle> stays = stayVehicleRepository.findByPlateStays(plate);

        Map<String, Long> totalMinutes = new HashMap<>();

        stays.parallelStream().forEach(stayVehicle -> {
            if (Objects.nonNull(stayVehicle.getInputDate()) && Objects.nonNull(stayVehicle.getOutputDate())) {
                Long resultMinutes = ChronoUnit.MINUTES.between(stayVehicle.getInputDate(), stayVehicle.getOutputDate());
                totalMinutes.compute(stayVehicle.getPlate().getPlate(), (plateKey, minutes) ->
                        minutes == null ? resultMinutes : minutes + resultMinutes);
            }
        });

        VehicleInfoReflection vehicleInfoReflection = vehicleRepository.getVehicleInfoGeneral(plate);

        return VehicleDetailsDto.builder()
                .plate(vehicleInfoReflection.getPlate())
                .name(vehicleInfoReflection.getName())
                .description(vehicleInfoReflection.getDescription())
                .cost(vehicleInfoReflection.getCost())
                .minutes(totalMinutes.get(plate))
                .totalCost(PaymentsService.multiplyFormater(new BigDecimal(vehicleInfoReflection.getCost()),
                        totalMinutes.get(plate)))
                .build();
    }

    public PageResponse<VehicleInfoReflection> pageableService(PageResponse<?> pageable) {
        PageResponse<VehicleInfoReflection> pageResponse = PageResponse.<VehicleInfoReflection>builder()
                .currentPage(pageable.getCurrentPage())
                .pageSize(pageable.getPageSize())
                .build();

        List<VehicleInfoReflection> result = this.vehicleRepository.pageRecords(pageable.getPageSize(),
                pageable.getCurrentPage() * pageable.getPageSize());

        Long totalElements = this.vehicleRepository.totalElements();

        pageResponse.setTotalElements(totalElements);

        pageResponse.setTotalPages((int) (totalElements / pageable.getPageSize()));

        pageResponse.setContent(result);

        return pageResponse;
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
