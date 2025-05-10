package com.exam.neology.eagv.parking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VehicleDetailsDto implements Serializable {

    private String plate;
    private String name;
    private String description;
    private String cost;
    private Long minutes;
    private String totalCost;

}
