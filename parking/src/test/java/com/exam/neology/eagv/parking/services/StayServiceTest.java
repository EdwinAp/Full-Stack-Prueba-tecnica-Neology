package com.exam.neology.eagv.parking.services;

import com.exam.neology.eagv.parking.dtos.StayVehicleDto;
import com.exam.neology.eagv.parking.dtos.VehicleUpDto;
import com.exam.neology.eagv.parking.entity.StayVehicle;
import com.exam.neology.eagv.parking.entity.Vehicle;
import com.exam.neology.eagv.parking.repository.StayVehicleRepository;
import com.exam.neology.eagv.parking.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StayServiceTest {

    @Mock
    private StayVehicleRepository stayVehicleRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private StayService stayService;

    @Test
    void testObtainStayByPlate() {
        StayVehicle stayVehicle = StayVehicle.builder()
                .inputDate(LocalDateTime.now())
                .outputDate(LocalDateTime.now().plusHours(2))
                .build();

        when(stayVehicleRepository.findByPlate("ABC123")).thenReturn(List.of(stayVehicle));

        List<StayVehicleDto> result = stayService.obtainStayByPlate("ABC123");
        assertEquals(1, result.size());
        assertEquals(stayVehicle.getInputDate(), result.get(0).getInputDate());
    }

    @Test
    void testInputVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("XYZ789");

        VehicleUpDto vehicleUpDto = new VehicleUpDto();
        vehicleUpDto.setPlate("XYZ789");

        when(vehicleRepository.findById("XYZ789")).thenReturn(Optional.of(vehicle));

        VehicleUpDto result = stayService.inputVehicle(vehicleUpDto);

        assertEquals("XYZ789", result.getPlate());
    }

}