package com.epam.esm.service.impl;

import com.epam.esm.Role;
import com.epam.esm.Status;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.persistence.UserDAO;
import com.epam.esm.service.UserService;
import com.epam.esm.service.entitydtomapper.impl.UserMapper;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return mapper.changeToDto(userDAO.find(id).orElseThrow(()
                -> new NoSuchResourceException(
                ExceptionCode.NO_SUCH_USER_FOUND.getErrorCode(), "id= " + id)));
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserDTO update(UserDTO user) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> find(PageInfo pageInfo, UserCriteriaInfo criteriaInfo) {
        List<User> users = userDAO.findAll(pageInfo, criteriaInfo);
        return getListUserDto(users);
    }

    @Transactional
    @Override
    public UserDTO create(UserDTO userDTO) {
        userDAO.findByEmail(userDTO.getUsername()).orElseThrow(() ->
                new ResourceAlreadyExistsException(ExceptionCode.USER_ALREADY_EXIST.getErrorCode(),
                        userDTO.getUsername()));
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPassword(userDTO.getPassword());
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        user.setCreateDate(Instant.now());
        user.setLastUpdateDate(Instant.now());

        userDAO.create(user);
        return userDTO;
    }

    @Transactional
    @Override
    public long getCount() {
        return userDAO.getCount();
    }

    private List<UserDTO> getListUserDto(List<User> users) {
        return users
                .stream()
                .map(mapper::changeToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return UserDTO.fromUser(user);
    }
}
