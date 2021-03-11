package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.OrderCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.Cart;
import com.epam.esm.dto.CartItem;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderItem;
import com.epam.esm.persistence.OrderDAO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.entitydtomapper.impl.OrderDtoMapper;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.validate.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final OrderDtoMapper orderDtoMapper;
    private final PaginationValidator paginationValidator;

    @Override
    public OrderDto findById(Long id) {
        Order order = orderDAO.find(id).orElseThrow(() ->
                new NoSuchResourceException(ExceptionCode.NO_SUCH_ORDER_FOUND.getErrorCode(), "id= " + id));

        return orderDtoMapper.changeToDto(order);
    }

    @Transactional
    @Override
    public OrderDto create(Cart cart) {
        Order order = new Order(cart.getUserId());
        order.setCreateDate(Instant.now());
        for (CartItem cartItem : cart.getCartItems()) {
            order.add(new OrderItem(cartItem));
        }
        order.setId(orderDAO.create(order));
        return orderDtoMapper.changeToDto(order);
    }

    @Override
    public long getCount() {
        return orderDAO.getCount();
    }

    @Override
    public List<OrderDto> find(PageInfo pageInfo, OrderCriteriaInfo criteriaInfo) {
        paginationValidator.validate(pageInfo);
        List<Order> orders = orderDAO.findAll(pageInfo, criteriaInfo);
        return getListOrderDto(orders);
    }

    private List<OrderDto> getListOrderDto(List<Order> orders) {
        return orders.stream()
                .map(orderDtoMapper::changeToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto create(OrderDto orderReadDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderDto update(OrderDto orderReadDto) {
        throw new UnsupportedOperationException();
    }
}
