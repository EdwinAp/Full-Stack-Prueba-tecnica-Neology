package com.exam.neology.eagv.parking.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CatVehicleDto implements Serializable {

    private Integer id;
    private String name;
    private String description;

}
