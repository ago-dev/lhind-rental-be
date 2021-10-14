package com.dago.service.user;

import com.dago.domain.User;
import com.dago.service.user.dto.req.UserSaveDto;
import com.dago.service.user.dto.res.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    List<UserDto> toDtos(List<User> users);
    User toEntity(UserSaveDto dto);
    List<User> toEntities(List<UserSaveDto> dtos);
}
