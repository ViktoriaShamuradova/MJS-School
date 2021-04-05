package com.epam.esm.service;

import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends CrudService<UserDTO, Long, UserCriteriaInfo>, UserDetailsService {

    long getCount();
    UserDTO register(RegistrationUserDto registrationUserDto);
}
