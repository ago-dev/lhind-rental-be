package com.dago.web;

import com.dago.service.user.UserService;
import com.dago.service.user.dto.res.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('USER_READ')")
    public ResponseEntity<Page<UserDto>> listUsers(@RequestParam(defaultValue = "1") int pageNo,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(userService.listUsers(PageRequest.of(pageNo - 1, pageSize)));
    }
}
