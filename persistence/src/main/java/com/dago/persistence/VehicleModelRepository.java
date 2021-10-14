package com.dago.persistence;

import com.dago.domain.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {
}
