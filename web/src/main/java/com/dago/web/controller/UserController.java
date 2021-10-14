package com.dago.web.controller;

import com.dago.service.user.PasswordService;
import com.dago.service.user.UserService;
import com.dago.service.user.dto.req.ChangePasswordDto;
import com.dago.service.user.dto.req.RequestResetPasswordDto;
import com.dago.service.user.dto.req.UserSaveDto;
import com.dago.service.user.dto.res.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;
    private final PasswordService passwordService;

    @GetMapping("/list")
//    @PreAuthorize("hasAnyAuthority('USER_READ')")
    public ResponseEntity<Page<UserDto>> listUsers(@RequestParam(defaultValue = "1") int pageNo,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(userService.listUsers(PageRequest.of(pageNo - 1, pageSize)));
    }

    @PostMapping
//    @PreAuthorize("hasAnyAuthority('USER_WRITE')")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserSaveDto userDto) {
        return ResponseEntity.ok().body(userService.createUser(userDto));
    }

    @PutMapping
//    @PreAuthorize("hasAnyAuthority('USER_WRITE')")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserSaveDto userDto) {
        return ResponseEntity.ok().body(userService.updateUser(userDto));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('USER_WRITE')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request-reset-password")
    public ResponseEntity<Void> requestResetPassword(@RequestBody @Valid RequestResetPasswordDto req) {
        passwordService.requestResetPassword(req.getEmail());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordDto req) {
        passwordService.changePassword(req.getResetToken(), req.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
