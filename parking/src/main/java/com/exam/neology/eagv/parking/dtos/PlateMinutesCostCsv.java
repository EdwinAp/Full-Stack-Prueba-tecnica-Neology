package com.exam.neology.eagv.parking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"Núm. placa ", "Tiempo estacionado (min.)", "Cantidad a pagar"})
public class PlateMinutesCostCsv implements Serializable {

    @JsonProperty("Núm. placa ")
    private String plate;
    @JsonProperty("Tiempo estacionado (min.)")
    private Long minutes;
    @JsonProperty("Cantidad a pagar")
    private String cost;

}
