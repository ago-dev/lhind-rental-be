package com.dago.web.controller;

import com.dago.service.user.UserService;
import com.dago.service.user.dto.req.UserSaveDto;
import com.dago.service.user.dto.res.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @PutMapping("/{id}")
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
}
