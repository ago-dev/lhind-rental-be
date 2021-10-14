package com.dago.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Table(name = "vehicle_model")
@Entity
public class VehicleModel extends BaseEntity<Integer> {
    @Column(name = "name")
    private String name;

    @Lob
    @Column(name="image")
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_category_id")
    private VehicleCategory vehicleCategory;

    @OneToMany(mappedBy = "vehicleModel")
    private List<Vehicle> vehicles;
}
