package com.epam.esm.service.entitydtomapper;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;

public interface UserMapper {

    User changeToEntity(UserDTO user);

    UserDTO changeToDto(User user);
}
