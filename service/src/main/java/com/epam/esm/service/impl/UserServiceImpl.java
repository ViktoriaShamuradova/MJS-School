package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.persistence.UserDAO;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification_builder.impl.UserSpecificationBuilder;
import com.epam.esm.service.UserService;
import com.epam.esm.service.entitydtomapper.impl.UserMapper;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.validate.PaginationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserMapper mapper;
    private final PaginationValidator paginationValidator;
    private final UserSpecificationBuilder specificationBuilder;


    @Autowired
    public UserServiceImpl(UserDAO userDAO,
                           UserMapper userMapper,
                           PaginationValidator paginationValidator,
                           UserSpecificationBuilder specificationBuilder) {
        this.mapper = userMapper;
        this.userDAO = userDAO;
        this.paginationValidator = paginationValidator;
        this.specificationBuilder = specificationBuilder;
    }

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
        paginationValidator.validate(pageInfo);
        List<Specification> specifications = specificationBuilder.build(criteriaInfo);
        List<User> users = userDAO.findAll(specifications,(int) pageInfo.getOffset(),(int) pageInfo.getLimit());
        return getListUserDto(users);
    }

    @Override
    public UserDTO create(UserDTO user) {
        throw new UnsupportedOperationException();
    }

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
}
