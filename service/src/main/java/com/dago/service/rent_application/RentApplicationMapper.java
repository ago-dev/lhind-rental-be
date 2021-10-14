package com.dago.service.rent_application;

import com.dago.domain.RentApplication;
import com.dago.service.rent_application.dto.req.RentApplicationReqDto;
import com.dago.service.rent_application.dto.req.RentApplicationUpdateReqDto;
import com.dago.service.rent_application.dto.res.RentApplicationResDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentApplicationMapper {
    RentApplication toEntity(RentApplicationReqDto dto);
    RentApplication toUpdateEntity(RentApplicationUpdateReqDto dto);
    List<RentApplicationResDto> toDtos(List<RentApplication> entities);
}
