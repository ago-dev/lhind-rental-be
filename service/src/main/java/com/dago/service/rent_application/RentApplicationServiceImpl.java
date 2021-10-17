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
import com.dago.service.rent_application.dto.res.RentApplicationPendingDto;
import com.dago.service.rent_application.dto.res.RentApplicationResDto;
import com.dago.service.rent_application.enums.RentApplicationStatusEnum;
import com.dago.service.user.enums.UserRole;
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

    private static final String STATUS_NOT_FOUND = "Status not found!";

    @Override
    public void createRentApplication(RentApplicationReqDto dto) {
        RentApplication rentApplication = rentApplicationMapper.toEntity(dto);
        User loggedUser = retrieveCurrentUserEntity();
        log.info("Logged user -> {}", loggedUser.getUsername());
        rentApplication.setApplicant(loggedUser);
        VehicleModel requestedModel = vehicleModelRepository.findById(dto.getVehicleModelId()).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle model not found!")
        );
        RentApplicationStatus status = rentApplicationStatusRepository.findByName(RentApplicationStatusEnum.PENDING.returnValue())
                .orElseThrow(() -> new ResourceNotFoundException(STATUS_NOT_FOUND));

        rentApplication.setVehicleModel(requestedModel);
        rentApplication.setRentApplicationStatus(status);
        rentApplicationRepository.save(rentApplication);
    }

    @Override
    public void updateRentApplication(Integer id, RentApplicationReqDto dto) {
        User loggedUser = retrieveCurrentUserEntity();
        RentApplication rentApplication = rentApplicationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Rent application not found!"));
        log.info("UPDATE -> " + loggedUser.getUsername());
        if (!rentApplication.getApplicant().getId().equals(loggedUser.getId())) {
            throw new UserNotAuthorizedException();
        } else {
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
        log.info(loggedUser.getUsername());
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

    @Override
    public Page<RentApplicationPendingDto> listAllPendingApplications(Pageable pageable) {
        checkForAdminCompetence();
        RentApplicationStatus status = rentApplicationStatusRepository.findByName(RentApplicationStatusEnum.PENDING.returnValue())
                .orElseThrow(() -> new ResourceNotFoundException(STATUS_NOT_FOUND));
        Page<RentApplication> applicationsPage = rentApplicationRepository.findAllByRentApplicationStatus(status, pageable);
        List<RentApplicationPendingDto> applicationDtos = rentApplicationMapper.toPendingApplicationDtos(applicationsPage.getContent());
        return new PageImpl<>(applicationDtos, pageable, applicationsPage.getTotalElements());
    }

    @Override
    public void reviewApplication(Integer id, boolean isApproved) {
        checkForAdminCompetence();
        RentApplication application = rentApplicationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Application not found!"));
        RentApplicationStatus rentApplicationStatus = rentApplicationStatusRepository.findByName(
                isApproved ? RentApplicationStatusEnum.APPROVED.returnValue() : RentApplicationStatusEnum.DECLINED.returnValue())
                .orElseThrow(() -> new ResourceNotFoundException(STATUS_NOT_FOUND));
        application.setRentApplicationStatus(rentApplicationStatus);
        rentApplicationRepository.save(application);
    }

    private void checkForAdminCompetence() {
        User loggedUser = retrieveCurrentUserEntity();
        if (!loggedUser.getRole().getName().equals(UserRole.ADMIN.returnValue())) {
            throw new UserNotAuthorizedException();
        }
    }

    private User retrieveCurrentUserEntity() {
        log.info(LoggedUser.getUsername());
        return userRepository.findByUsername(LoggedUser.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("User not found!")
        );
    }
}
