package com.dago.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter @Setter
@Table(name = "vehicle")
@Entity
public class Vehicle extends BaseEntity<Integer> {
    @Column(name = "daily_cost")
    private BigDecimal dailyCost;

    @Column(name = "description")
    private String description;;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_model_id")
    private VehicleModel vehicleModel;
}
