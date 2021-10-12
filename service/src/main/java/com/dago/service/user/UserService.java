package com.dago.service.user;

import com.dago.service.user.dto.res.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDto> listUsers(Pageable pageable);
}
