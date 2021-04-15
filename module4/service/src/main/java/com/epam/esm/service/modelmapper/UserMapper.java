package com.epam.esm.service.modelmapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User toEntity(UserDto dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, User.class);
    }

    public UserDto toDTO(User entity) {

        UserDto userDTO =  Objects.isNull(entity) ? null : modelMapper.map(entity, UserDto.class);
        userDTO.setUsername(entity.getEmail());
        return userDTO;
    }
}
