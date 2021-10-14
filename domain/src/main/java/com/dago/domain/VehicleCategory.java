package com.dago.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter @Setter
@Table(name = "vehicle_category")
@Entity
public class VehicleCategory extends BaseEntity<Short> {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name="image")
    private byte[] image;

    @OneToMany(mappedBy = "vehicleCategory")
    private List<VehicleModel> vehicleModels;
}
