package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.OrderCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.Cart;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.persistence.OrderDAO;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification_builder.impl.OrderSpecificationBuilder;
import com.epam.esm.service.entitydtomapper.impl.OrderDtoMapper;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.validate.PaginationValidator;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderDAO orderDAO;
    @Mock
    private OrderDtoMapper orderDtoMapper;
    @Mock
    private PaginationValidator paginationValidator;
    @Mock
    private OrderSpecificationBuilder orderSpecificationBuilder;

    @Test
    public void findById_shouldFind() {
        Order o = new Order();
        OrderDto orderDto = new OrderDto();
        o.setId(1L);
        when(orderDAO.find(anyLong())).thenReturn(Optional.of(o));
        when(orderDtoMapper.changeToDto(o)).thenReturn(orderDto);
        orderService.findById(o.getId());
        verify(orderDAO).find(o.getId());
        verify(orderDtoMapper).changeToDto(o);

        Assertions.assertThat(orderService.findById(o.getId()).equals(orderDto));
    }

    @Test
    public void findById_shouldThrowException() {
        when(orderDAO.find(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(()
                -> orderService.findById(anyLong()))
                .isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void create_shouldCreate_WithCart() {
        Order o = new Order();
        OrderDto orderDto = new OrderDto();
        o.setId(1L);
        when(orderDAO.create(any())).thenReturn(o.getId());
        when(orderDtoMapper.changeToDto(any())).thenReturn(orderDto);

        orderService.create(new Cart());

        Assertions.assertThat(orderService.create(new Cart()).equals(orderDto));
    }
    @Test
    public void find_shouldFindListOfOrders() {
        //given
        List<Order> orders = new ArrayList<>();
        Order o = new Order();
        orders.add(o);

        List<Specification> specifications = new ArrayList<>();
        OrderCriteriaInfo criteriaInfo = new OrderCriteriaInfo();
        PageInfo pageInfo = new PageInfo(1, 1, 1);

        when(orderSpecificationBuilder.build(any()))
                .thenReturn(specifications);
        when(orderDAO.findAll(any(), anyInt(), anyInt()))
                .thenReturn(orders);
        //when
        orderService.find(pageInfo, criteriaInfo);
        //then
        verify(paginationValidator).validate(pageInfo);
        verify(orderSpecificationBuilder).build(any());
        verify(orderDAO)
                .findAll(specifications, (int) pageInfo.getOffset(), (int) pageInfo.getLimit());
        verify(orderDtoMapper).changeToDto(o);
    }
    @Test
    public void create_shouldThrownException() {
        assertThatThrownBy(() -> orderService.update(new OrderDto()))
                .isInstanceOf(UnsupportedOperationException.class);
    }
    @Test
    public void delete_shouldThrownException() {
        assertThatThrownBy(() -> orderService.delete(1L))
                .isInstanceOf(UnsupportedOperationException.class);
    }
    @Test
    public void update_shouldThrownException() {
        assertThatThrownBy(() -> orderService.update(new OrderDto()))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
