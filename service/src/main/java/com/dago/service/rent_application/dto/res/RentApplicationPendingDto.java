package com.dago.service.rent_application.dto.res;

import com.dago.service.user.dto.res.UserDto;
import com.dago.service.vehicle_model.dto.res.VehicleModelDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class RentApplicationPendingDto {
    private Integer id;
    private VehicleModelDto vehicleModel;
    private LocalDate fromDate;
    private LocalDate toDate;
    private UserDto applicant;
}
