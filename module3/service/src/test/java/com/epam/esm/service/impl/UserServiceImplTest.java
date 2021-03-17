package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.persistence.UserDAO;
import com.epam.esm.service.entitydtomapper.impl.UserMapper;
import com.epam.esm.service.exception.NoSuchResourceException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDAO userDAO;
    @Mock
    private UserMapper mapper;

    @Test
    public void findById_shouldFind() {
        User user = new User();
        user.setId(1L);
        when(userDAO.find(anyLong())).thenReturn(Optional.of(user));

        userService.findById(user.getId());

        verify(mapper).changeToDto(user);
        verify(userDAO).find(user.getId());
    }

    @Test
    public void findById_shouldThrownException() {
        when(userDAO.find(anyLong())).thenThrow(NoSuchResourceException.class);
        assertThatThrownBy(() -> userService.findById(anyLong())).isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void delete_shouldThrownException() {
        assertThatThrownBy(() -> userService.delete(1L))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void update_shouldThrownException() {
        assertThatThrownBy(() -> userService.update(new UserDTO()))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void find_shouldFindListOfTags() {
        //given
        List<User> users = new ArrayList<>();
        User u = new User();
        users.add(u);

        UserCriteriaInfo criteriaInfo = new UserCriteriaInfo();
        PageInfo pageInfo = new PageInfo(1, 1);

        when(userDAO.findAll(any(), any()))
                .thenReturn(users);
        //when
        userService.find(pageInfo, criteriaInfo);
        //then
        verify(userDAO)
                .findAll(pageInfo, criteriaInfo);
        verify(mapper).changeToDto(u);
    }

    @Test
    public void create_shouldThrownException() {
        assertThatThrownBy(() -> userService.update(new UserDTO()))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void getCount_shouldGet() {
        when(userDAO.getCount()).thenReturn(1L);
        Assertions.assertThat(userService.getCount() == 1L);
    }
}
