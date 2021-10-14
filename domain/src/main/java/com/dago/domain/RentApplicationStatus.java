package com.dago.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter @Setter
@Table(name ="rent_application_status")
@Entity
public class RentApplicationStatus extends BaseEntity<Short> {
    @Column(name = "name")
    public String name;
}
