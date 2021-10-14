package com.dago.service.rent_application;

import com.dago.service.rent_application.dto.req.RentApplicationReqDto;
import com.dago.service.rent_application.dto.req.RentApplicationUpdateReqDto;
import com.dago.service.rent_application.dto.res.RentApplicationResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentApplicationService {
    void createRentApplication(RentApplicationReqDto dto);
    void updateRentApplication(RentApplicationUpdateReqDto dto);
    Page<RentApplicationResDto> listApplications(Pageable pageable);
    void deleteApplication(Integer id);
    Page<RentApplicationResDto> listAllPendingApplications(Pageable pageable);

}
