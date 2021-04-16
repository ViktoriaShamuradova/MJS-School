package com.epam.esm.service.modelmapper.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.service.modelmapper.AbstractModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper extends AbstractModelMapper<UserDto, User, Long> {

    public UserMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    public User toEntity(UserDto dto) {
        return Objects.isNull(dto) ? null : super.getModelMapper().map(dto, User.class);
    }

    public UserDto toDTO(User entity) {
        UserDto userDTO =  Objects.isNull(entity) ? null : super.getModelMapper().map(entity, UserDto.class);
        userDTO.setUsername(entity.getEmail());
        return userDTO;
    }
}
