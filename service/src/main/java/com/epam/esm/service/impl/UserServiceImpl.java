package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.CriteriaInfo;
import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.persistence.UserDAO;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.service.UserService;
import com.epam.esm.service.entitydtomapper.UserMapper;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserMapper userMapper) {
        this.mapper = userMapper;
        this.userDAO = userDAO;
    }

//    @Override
//    public List<UserDTO> findAll(PageInfo pageInfo) {
//        int pageNumber = pageInfo.getCurrentPage();
//        int limit = pageInfo.getLimit();
//        int offset = (pageNumber * limit) - limit;
//        return null;
//        //return getList(userDAO.findAll(offset, limit));
//    }

    @Override
    public UserDTO findById(Long id) {
        return mapper.changeToDto(userDAO.find(id).orElseThrow(() -> new NoSuchResourceException(
                ExceptionCode.NO_SUCH_USER_FOUND.getErrorCode(), "id= " + id)));

    }

    @Override
    public boolean delete(Long id) {
        throw new NotSupportedException(ExceptionCode.NOT_SUPPORTED_OPERATION.getErrorCode());
    }

    @Override
    public UserDTO update(UserDTO user) {
        throw new NotSupportedException(ExceptionCode.NOT_SUPPORTED_OPERATION.getErrorCode());
    }


    @Override
    public List<UserDTO> find(PageInfo pageInfo, UserCriteriaInfo criteriaInfo) {
        return null;
    }

    @Override
    public UserDTO create(UserDTO user) {
        throw new NotSupportedException(ExceptionCode.NOT_SUPPORTED_OPERATION.getErrorCode());
    }

    @Override
    public long getCount() {
        return userDAO.getCount();
    }

    private List<UserDTO> getList(List<User> users) {
        return users
                .stream()
                .map(user -> {
                    return mapper.changeToDto(user);
                })
                .collect(Collectors.toList());
    }
}
