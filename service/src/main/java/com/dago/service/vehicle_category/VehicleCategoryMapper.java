package com.dago.service.vehicle_category;

import com.dago.domain.VehicleCategory;
import com.dago.service.vehicle_category.dto.res.VehicleCategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleCategoryMapper {
    VehicleCategoryDto toDto(VehicleCategory vehicleCategory);
    List<VehicleCategoryDto> toDtos(List<VehicleCategory> vehicleCategories);
}
