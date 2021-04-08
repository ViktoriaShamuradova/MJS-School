package com.epam.esm.persistence.impl;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.entity.User;
import com.epam.esm.persistence.UserDao;
import com.epam.esm.persistence.dataspecification.UserSpecification;
import com.epam.esm.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll(UserCriteriaInfo criteriaInfo, PageInfo pageInfo) {
        UserSpecification specification = new UserSpecification(criteriaInfo);
        return userRepository.findAll(specification, PageRequest.of(pageInfo.getCurrentPage() - 1, pageInfo.getLimit()))
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User registerUser) {
        return userRepository.save(registerUser);
    }

    @Override
    public long getCount() {
        return userRepository.count();
    }
}
