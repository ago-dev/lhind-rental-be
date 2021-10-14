package com.dago.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Table(name="role")
@Entity
public class Role extends BaseEntity<Short>{
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "roles_privileges",
            joinColumns        = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private List<Privilege> privileges;
}
