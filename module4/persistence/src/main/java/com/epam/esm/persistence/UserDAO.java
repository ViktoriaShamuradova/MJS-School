package com.epam.esm.persistence;

import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.entity.User;

import java.util.Optional;

public interface UserDAO extends CrudDAO<User, Long, UserCriteriaInfo>{

    long getCount();

    Optional<User> findByEmail(String email);
}
