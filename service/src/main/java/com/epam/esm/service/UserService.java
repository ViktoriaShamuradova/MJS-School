package com.epam.esm.service;

import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.dto.UserDTO;

public interface UserService extends CrudService<UserDTO, Long, UserCriteriaInfo> {
    long getCount();
}
