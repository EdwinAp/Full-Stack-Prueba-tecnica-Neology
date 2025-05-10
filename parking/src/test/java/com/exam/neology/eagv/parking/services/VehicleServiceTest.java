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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@RunWith(MockitoJUnitRunner.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private CatVehicleRepository catVehicleRepository;
    @Mock
    private StayVehicleRepository stayVehicleRepository;


    @Test
    void getVehicleInfoGeneral_existingPlateWithStays_returnsVehicleDetailsDto() {
        String plate = "ABC-123";

        VehicleInfoReflection mockedVehicleInfo = mock(VehicleInfoReflection.class);
        when(mockedVehicleInfo.getPlate()).thenReturn(plate);
        when(mockedVehicleInfo.getName()).thenReturn("Carro de Prueba");
        when(mockedVehicleInfo.getDescription()).thenReturn("Un vehículo para pruebas unitarias");
        when(mockedVehicleInfo.getCost()).thenReturn("10.0");

        List<StayVehicle> stays = List.of(
                StayVehicle.builder().plate(Vehicle.builder().plate("ABC-123").build()).inputDate(LocalDateTime.now().minusHours(2)).outputDate(LocalDateTime.now()).build(),
                StayVehicle.builder().plate(Vehicle.builder().plate("ABC-123").build()).inputDate(LocalDateTime.now().minusHours(5)).outputDate(LocalDateTime.now().minusHours(3)).build()
        );

        when(vehicleRepository.getVehicleInfoGeneral(plate)).thenReturn(mockedVehicleInfo);
        when(stayVehicleRepository.findByPlateStays(plate)).thenReturn(stays);

        VehicleDetailsDto result = vehicleService.getVehicleInfoGeneral(plate);

        assertNotNull(result);
        assertEquals(plate, result.getPlate());
        assertEquals(mockedVehicleInfo.getName(), result.getName());
        assertEquals(mockedVehicleInfo.getDescription(), result.getDescription());
        assertEquals(mockedVehicleInfo.getCost(), result.getCost());
    }

    @Test
    void pageableService_validPageRequest_returnsPageResponseWithContent() {
        PageResponse<Object> pageable = PageResponse.builder().currentPage(0).pageSize(2).build();

        long totalElements = 3;

        List pages = List.of();

        when(vehicleRepository.pageRecords(2, 0)).thenReturn(pages);
        when(vehicleRepository.totalElements()).thenReturn(totalElements);

        PageResponse<VehicleInfoReflection> result = vehicleService.pageableService(pageable);

        assertNotNull(result);
        assertEquals(0, result.getCurrentPage());
        assertEquals(2, result.getPageSize());
        assertEquals(totalElements, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(pages, result.getContent());
    }

    @Test
    void getAllCatVehicle_existingCategories_returnsListOfCatVehicleDto() {
        List<CatVehicle> mockCatVehicles = List.of(
                new CatVehicle(1, "Carro", "Vehículo estándar", null, List.of()),
                new CatVehicle(2, "Moto", "Vehículo de dos ruedas", null, List.of())
        );
        when(catVehicleRepository.findAll()).thenReturn(mockCatVehicles);

        List<CatVehicleDto> result = vehicleService.getAllCatVehicle();

        assertNotNull(result);
        assertEquals(mockCatVehicles.size(), result.size());
        assertEquals(mockCatVehicles.get(0).getId(), result.get(0).getId());
        assertEquals(mockCatVehicles.get(0).getName(), result.get(0).getName());
        assertEquals(mockCatVehicles.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(mockCatVehicles.get(1).getId(), result.get(1).getId());
        assertEquals(mockCatVehicles.get(1).getName(), result.get(1).getName());
        assertEquals(mockCatVehicles.get(1).getDescription(), result.get(1).getDescription());
    }

    @Test
    void upVehicle_validVehicleUpDtoAndExistingCatVehicle_savesNewVehicleAndReturnsDto() {
        VehicleUpDto vehicleUpDto = new VehicleUpDto("XYZ-012", 1);
        CatVehicle mockCatVehicle = new CatVehicle(1, "Carro", "Vehículo estándar",
                null, List.of());
        Vehicle expectedVehicle = Vehicle.builder().plate("XYZ-012").catVehicle(mockCatVehicle).build();

        when(catVehicleRepository.findById(1)).thenReturn(Optional.of(mockCatVehicle));

        VehicleUpDto result = vehicleService.upVehicle(vehicleUpDto);

        assertNotNull(result);
        assertEquals(vehicleUpDto.getPlate(), result.getPlate());
        assertEquals(vehicleUpDto.getCatVehicle(), result.getCatVehicle());
    }

}