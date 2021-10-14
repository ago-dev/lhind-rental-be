package com.dago.service.vehicle_category;

import com.dago.persistence.VehicleCategoryRepository;
import com.dago.service.vehicle_category.dto.res.VehicleCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class VehicleCategoryServiceImpl implements VehicleCategoryService{
    private final VehicleCategoryRepository vehicleCategoryRepository;
    private final VehicleCategoryMapper vehicleCategoryMapper;

    @Override
    public List<VehicleCategoryDto> listVehicleCategories() {
        return vehicleCategoryMapper.toDtos(
                vehicleCategoryRepository.findAll()
        );
    }
}
