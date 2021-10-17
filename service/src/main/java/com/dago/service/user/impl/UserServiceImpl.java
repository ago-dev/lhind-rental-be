package com.dago.service.user.impl;

import com.dago.domain.Role;
import com.dago.domain.User;
import com.dago.persistence.RoleRepository;
import com.dago.persistence.UserRepository;
import com.dago.service.exception.ResourceNotFoundException;
import com.dago.service.user.UserMapper;
import com.dago.service.user.UserService;
import com.dago.service.user.dto.req.UserSaveDto;
import com.dago.service.user.dto.res.UserDto;
import com.dago.service.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserDto> listUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserDto> userDtos = mapper.toDtos(usersPage.getContent());
        return new PageImpl<>(userDtos, pageable, usersPage.getTotalElements());
    }

    @Override
    public UserDto createUser(@Valid UserSaveDto userDto) {
        User userToCreate = mapper.toEntity(userDto);
        Role role = roleRepository.findByName(UserRole.MEMBER.returnValue()).orElseThrow(
                () -> new ResourceNotFoundException("Role not found!")
        );
        userToCreate.setRole(role);
        String generatedPassword = RandomStringUtils.randomAlphabetic(10);
        userToCreate.setPassword(passwordEncoder.encode(generatedPassword));
        return mapper.toDto(userRepository.save(userToCreate));
    }

    @Override
    public UserDto updateUser(Long id, UserSaveDto userDto) {
        User userToUpdate = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User does not exist!"));

        userToUpdate.setUsername(userDto.getUsername());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());

        return mapper.toDto(userRepository.save(userToUpdate));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
