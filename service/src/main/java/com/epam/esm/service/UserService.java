package com.epam.esm.service;

import com.epam.esm.dto.UserDTO;

public interface UserService extends CrudService<UserDTO, Long>{
    long getCount();
}
