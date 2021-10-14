package com.dago.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Table(name = "rent_application")
@Entity
public class RentApplication extends BaseEntity<Integer> {
    @Column(name = "fromDate")
    private LocalDate fromDate;

    @Column(name = "toDate")
    private LocalDate toDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_model_id")
    private VehicleModel vehicleModel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User applicant;

    @ManyToOne
    @JoinColumn(name = "rent_application_status_id")
    private RentApplicationStatus rentApplicationStatus;
}
