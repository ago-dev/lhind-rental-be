package com.dago.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter @Setter
@Table(name = "privilege")
@Entity
public class Privilege extends BaseEntity<Short> {
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private List<Role> roles;
}
