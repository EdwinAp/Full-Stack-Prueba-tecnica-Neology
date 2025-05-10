package com.exam.neology.eagv.parking.controllers;

import com.exam.neology.eagv.parking.dtos.StayVehicleDto;
import com.exam.neology.eagv.parking.dtos.VehicleUpDto;
import com.exam.neology.eagv.parking.services.StayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/stays")
public class StayController {

    private final StayService stayService;

    public StayController(
            StayService stayService
    ) {
        this.stayService = stayService;
    }

    @GetMapping("/{plate}")
    public ResponseEntity<List<StayVehicleDto>> getStayOfVehicle(@PathVariable String plate) {
        List<StayVehicleDto> result = stayService.obtainStayByPlate(plate);
        return ResponseEntity.ok(result);
    }

    @PostMapping("input")
    public ResponseEntity<?> inputVehicle(@RequestBody VehicleUpDto vehicleUpDto) {

        VehicleUpDto result = this.stayService.inputVehicle(vehicleUpDto);

        return ResponseEntity.ok(result);
    }

    @PostMapping("output")
    public ResponseEntity<?> outputVehicle(@RequestBody VehicleUpDto vehicleUpDto) {

        VehicleUpDto result = this.stayService.outputVehicle(vehicleUpDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("current")
    public ResponseEntity<?> deleteCurrentMonth() {
        Boolean isDeleted = this.stayService.deleteCurrentMonth();
        return ResponseEntity.ok(isDeleted);
    }

}
