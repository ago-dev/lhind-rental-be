package com.dago.service.rent_application;

import com.dago.domain.RentApplication;
import com.dago.domain.RentApplicationStatus;
import com.dago.domain.User;
import com.dago.domain.VehicleModel;
import com.dago.persistence.RentApplicationRepository;
import com.dago.persistence.RentApplicationStatusRepository;
import com.dago.persistence.UserRepository;
import com.dago.persistence.VehicleModelRepository;
import com.dago.service.LoggedUser;
import com.dago.service.exception.ResourceNotFoundException;
import com.dago.service.exception.UserNotAuthorizedException;
import com.dago.service.rent_application.dto.req.RentApplicationReqDto;
import com.dago.service.rent_application.dto.req.RentApplicationUpdateReqDto;
import com.dago.service.rent_application.dto.res.RentApplicationResDto;
import com.dago.service.rent_application.enums.RentApplicationStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class RentApplicationServiceImpl implements RentApplicationService {
    private final RentApplicationRepository rentApplicationRepository;
    private final UserRepository userRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final RentApplicationStatusRepository rentApplicationStatusRepository;
    private final RentApplicationMapper rentApplicationMapper;

    @Override
    public void createRentApplication(RentApplicationReqDto dto) {
        log.info("DTO -> {}", dto.getFromDate());
        RentApplication rentApplication = rentApplicationMapper.toEntity(dto);
        User loggedUser = retrieveCurrentUserEntity();
        rentApplication.setApplicant(loggedUser);
        VehicleModel requestedModel = vehicleModelRepository.findById(dto.getVehicleModelId()).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle model not found!")
        );
        RentApplicationStatus status = rentApplicationStatusRepository.findByName(RentApplicationStatusEnum.PENDING.returnValue())
                .orElseThrow( () -> new ResourceNotFoundException("Status not found!"));

        rentApplication.setVehicleModel(requestedModel);
        rentApplication.setRentApplicationStatus(status);
        rentApplicationRepository.save(rentApplication);
    }

    @Override
    public void updateRentApplication(RentApplicationUpdateReqDto dto) {
        User loggedUser = retrieveCurrentUserEntity();
        RentApplication rentApplication = rentApplicationRepository.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Rent application not found!"));

        if (!rentApplication.getApplicant().getId().equals(loggedUser.getId())) {
            throw new UserNotAuthorizedException();
        }else {
            rentApplication.setFromDate(dto.getFromDate());
            rentApplication.setToDate(dto.getToDate());
        }

        if (!dto.getVehicleModelId().equals(rentApplication.getVehicleModel().getId())) {
            VehicleModel requestedModel = vehicleModelRepository.findById(dto.getVehicleModelId()).orElseThrow(
                    () -> new ResourceNotFoundException("Vehicle model not found!")
            );
            rentApplication.setVehicleModel(requestedModel);
        }

        rentApplicationRepository.save(rentApplication);
    }

    @Override
    public Page<RentApplicationResDto> listApplications(Pageable pageable) {
        User loggedUser = retrieveCurrentUserEntity();
        Page<RentApplication> applicationsPage = rentApplicationRepository.findAllByApplicant(loggedUser, pageable);
        List<RentApplicationResDto> applicationDtos = rentApplicationMapper.toDtos(applicationsPage.getContent());
        return new PageImpl<>(applicationDtos, pageable, applicationsPage.getTotalElements());
    }

    @Override
    public void deleteApplication(Integer id) {
        User loggedUser = retrieveCurrentUserEntity();
        RentApplication rentApplication = rentApplicationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Rent application not found!"));
        if (!rentApplication.getApplicant().getId().equals(loggedUser.getId())) {
            throw new UserNotAuthorizedException();
        }

        rentApplicationRepository.delete(rentApplication);
    }

    private User retrieveCurrentUserEntity() {
        return userRepository.findByUsername(LoggedUser.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("User not found!")
        );
    }
}
