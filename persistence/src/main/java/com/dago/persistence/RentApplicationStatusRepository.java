package com.dago.persistence;

import com.dago.domain.RentApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentApplicationStatusRepository extends JpaRepository<RentApplicationStatus, Short> {
    Optional<RentApplicationStatus> findByName(String name);
}
