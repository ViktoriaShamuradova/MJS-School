package com.epam.esm.service.modelmapper;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User toEntity(UserDTO dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, User.class);
    }

    public UserDTO toDTO(User entity) {

        UserDTO userDTO =  Objects.isNull(entity) ? null : modelMapper.map(entity, UserDTO.class);
        userDTO.setUsername(entity.getEmail());
        return userDTO;
    }
}
