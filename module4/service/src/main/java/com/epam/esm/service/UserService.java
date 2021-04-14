package com.epam.esm.service;

import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDTO;

public interface UserService extends CrudService<UserDTO, Long, UserCriteriaInfo> {

    UserDTO create(RegistrationUserDto registrationUserDto);
}
