package com.exam.neology.eagv.parking.controllers;

import com.exam.neology.eagv.parking.dtos.CatVehicleDto;
import com.exam.neology.eagv.parking.dtos.PageResponse;
import com.exam.neology.eagv.parking.dtos.VehicleUpDto;
import com.exam.neology.eagv.parking.entity.VehicleDetailsDto;
import com.exam.neology.eagv.parking.entity.reflection.VehicleInfoReflection;
import com.exam.neology.eagv.parking.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/{plate}")
    public ResponseEntity<VehicleDetailsDto> getVehicleData(@PathVariable String plate) {
        VehicleDetailsDto reflection = vehicleService.getVehicleInfoGeneral(plate);
        return ResponseEntity.ok(reflection);
    }

    @GetMapping("/catalog")
    public ResponseEntity<List<CatVehicleDto>> getAllCatVehicle() {
        return ResponseEntity.ok(vehicleService.getAllCatVehicle());
    }

    @PostMapping("/up")
    public ResponseEntity<VehicleUpDto> upVehicles(@RequestBody VehicleUpDto vehicleUpDto) {

        VehicleUpDto result = vehicleService.upVehicle(vehicleUpDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/pageable")
    public ResponseEntity<PageResponse<VehicleInfoReflection>> pageableController(
            @RequestBody PageResponse<?> pageResponse) {

        PageResponse<VehicleInfoReflection> result = vehicleService.pageableService(pageResponse);

        return ResponseEntity.ok(result);
    }

}
