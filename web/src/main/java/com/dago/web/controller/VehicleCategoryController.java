package com.dago.web.controller;

import com.dago.service.vehicle_category.VehicleCategoryService;
import com.dago.service.vehicle_category.dto.res.VehicleCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/vehicle-category")
@RestController
public class VehicleCategoryController {
    private final VehicleCategoryService vehicleCategoryService;

    @GetMapping("/list")
    public ResponseEntity<List<VehicleCategoryDto>> listVehicleCategories() {
        return ResponseEntity.ok().body(vehicleCategoryService.listVehicleCategories());
    }
}
