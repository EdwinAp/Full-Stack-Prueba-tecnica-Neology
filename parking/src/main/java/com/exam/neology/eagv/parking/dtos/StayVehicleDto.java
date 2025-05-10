package com.exam.neology.eagv.parking.dtos;

import com.exam.neology.eagv.parking.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StayVehicleDto implements Serializable {

    private Vehicle plate;
    private LocalDateTime inputDate;
    private LocalDateTime outputDate;

}
