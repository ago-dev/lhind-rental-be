package com.dago.web.controller;

import com.dago.service.rent_application.RentApplicationService;
import com.dago.service.rent_application.dto.req.RentApplicationReqDto;
import com.dago.service.rent_application.dto.res.RentApplicationPendingDto;
import com.dago.service.rent_application.dto.res.RentApplicationResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/application")
@RestController
public class RentApplicationController {
    private final RentApplicationService rentApplicationService;

    @GetMapping("/list")
    //    @PreAuthorize("hasAnyAuthority('APPLICATION_READ')")
    public ResponseEntity<Page<RentApplicationResDto>> listApplications(@RequestParam(defaultValue = "1") int pageNo,
                                                                        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(rentApplicationService.listApplications(PageRequest.of(pageNo - 1, pageSize)));
    }

    @PostMapping
    //    @PreAuthorize("hasAnyAuthority('APPLICATION_WRITE')")
    public ResponseEntity<Void> createApplication(@Valid @RequestBody RentApplicationReqDto dto) {
        rentApplicationService.createRentApplication(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping({"/{id}"})
    //    @PreAuthorize("hasAnyAuthority('APPLICATION_WRITE')")
    public ResponseEntity<Void> updateApplication(@PathVariable Integer id, @Valid @RequestBody RentApplicationReqDto dto) {
        rentApplicationService.updateRentApplication(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    //    @PreAuthorize("hasAnyAuthority('APPLICATION_WRITE')")
    public ResponseEntity<Void> deleteApplication(@PathVariable Integer id) {
        rentApplicationService.deleteApplication(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/pending")
    //    @PreAuthorize("hasAnyAuthority('APPLICATION_WRITE')")
    public ResponseEntity<Page<RentApplicationPendingDto>> listPendingApplications(@RequestParam(defaultValue = "1") int pageNo,
                                                                                       @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(rentApplicationService.listAllPendingApplications(PageRequest.of(pageNo - 1, pageSize)));
    }

    @PutMapping("/pending")
    public ResponseEntity<Void> reviewApplication(@RequestParam Integer id, @RequestParam boolean isApproved) {
        rentApplicationService.reviewApplication(id, isApproved);
        return ResponseEntity.ok().build();
    }
}
