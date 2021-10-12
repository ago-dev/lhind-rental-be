package com.dago.service.user;

import com.dago.domain.User;
import com.dago.persistence.UserRepository;
import com.dago.service.user.dto.res.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public Page<UserDto> listUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserDto> userDtos = mapper.toDtos(usersPage.getContent());
        return new PageImpl<>(userDtos, pageable, usersPage.getTotalElements());
    }
}
