package com.exam.neology.eagv.parking.controllers;

import com.exam.neology.eagv.parking.dtos.CatVehicleDto;
import com.exam.neology.eagv.parking.dtos.VehicleUpDto;
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

    @GetMapping("/catalog")
    public ResponseEntity<List<CatVehicleDto>> getAllCatVehicle() {
        return ResponseEntity.ok(vehicleService.getAllCatVehicle());
    }

    @PostMapping("/up")
    public ResponseEntity<VehicleUpDto> upVehicles(@RequestBody VehicleUpDto vehicleUpDto) {

        VehicleUpDto result = vehicleService.upVehicle(vehicleUpDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


}
