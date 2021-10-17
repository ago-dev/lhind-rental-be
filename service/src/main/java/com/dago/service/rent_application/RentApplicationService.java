package com.dago.service.rent_application;

import com.dago.service.rent_application.dto.req.RentApplicationReqDto;
import com.dago.service.rent_application.dto.res.RentApplicationPendingDto;
import com.dago.service.rent_application.dto.res.RentApplicationResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentApplicationService {
    void createRentApplication(RentApplicationReqDto dto);
    void updateRentApplication(Integer id, RentApplicationReqDto dto);
    Page<RentApplicationResDto> listApplications(Pageable pageable);
    void deleteApplication(Integer id);
    Page<RentApplicationPendingDto> listAllPendingApplications(Pageable pageable);
    void reviewApplication(Integer id, boolean isApproved);
}
