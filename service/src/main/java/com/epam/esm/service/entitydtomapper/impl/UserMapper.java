package com.epam.esm.service.entitydtomapper.impl;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.service.entitydtomapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements DtoMapper<User, UserDTO> {

    @Override
    public User changeToEntity(UserDTO user) {
        User u = new User();
        u.setId(user.getId());
        u.setLastUpdateDate(user.getUpdateLastDate());
        u.setName(user.getName());
        u.setSurname(user.getSurname());
        u.setCreateDate(user.getCreateDate());
        return u;
    }

    @Override
    public UserDTO changeToDto(User user) {
        UserDTO u = new UserDTO();
        u.setId(user.getId());
        u.setLastUpdateDate(user.getUpdateLastDate());
        u.setName(user.getName());
        u.setSurname(user.getSurname());
        u.setCreateDate(user.getCreateDate());
        return u;
    }
}
