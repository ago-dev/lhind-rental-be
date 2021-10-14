package com.dago.service.vehicle_model;

import com.dago.domain.VehicleModel;
import com.dago.service.vehicle_model.dto.res.VehicleModelDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleModelMapper {
    VehicleModelDto toDto(VehicleModel vehicleModel);
    List<VehicleModelDto> toDtos(List<VehicleModel> vehicleModels);
}
