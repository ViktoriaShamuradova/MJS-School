package com.epam.esm.persistence;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAll(UserCriteriaInfo criteriaInfo, PageInfo pageInfo);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    User save(User registerUser);
    long getCount();
}
