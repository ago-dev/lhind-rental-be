package com.dago.service.vehicle_category.dto.res;

import com.dago.domain.VehicleModel;
import com.dago.service.vehicle_model.dto.res.VehicleModelDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class VehicleCategoryDto {
    private Short id;
    private String name;
    private String description;
    private List<VehicleModelDto> vehicleModels;
}
