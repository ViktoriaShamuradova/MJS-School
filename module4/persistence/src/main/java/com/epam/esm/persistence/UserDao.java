package com.epam.esm.persistence;

import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAll(UserCriteriaInfo criteriaInfo, Pageable pageable);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    User save(User registerUser);
    long getCount();
}
