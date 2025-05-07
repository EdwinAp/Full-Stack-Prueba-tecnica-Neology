package com.exam.neology.eagv.parking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "vehicles")
public class Vehicle implements Serializable {

    @Id
    private String plate;
    @ManyToOne
    @JoinColumn(name = "cat_vehicles_id")
    private CatVehicle catVehicle;
    @OneToMany(mappedBy = "plate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StayVehicle> stayVehicles;

}
