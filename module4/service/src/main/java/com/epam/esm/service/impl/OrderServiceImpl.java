package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.OrderCriteriaInfo;
import com.epam.esm.dto.Cart;
import com.epam.esm.dto.CartItem;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderItem;
import com.epam.esm.persistence.CertificateDao;
import com.epam.esm.persistence.OrderDao;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.modelmapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDAO;
    private final OrderMapper mapper;
    private final CertificateDao certificateDao;

    @Override
    public OrderDto findById(Long id) {
        Order order = orderDAO.findById(id).orElseThrow(() ->
                new NoSuchResourceException(ExceptionCode.NO_SUCH_ORDER_FOUND.getErrorCode(), "id= " + id));

        return mapper.toDTO(order);
    }

    @Transactional
    @Override
    public OrderDto create(Cart cart) {
        Order order = new Order(cart.getUserId());
        order.setCreateDate(Instant.now());
        for (CartItem cartItem : cart.getCartItems()) {
            Certificate certificate = certificateDao.findById(cartItem.getIdCertificate()).orElseThrow(() ->
                    new NoSuchResourceException(ExceptionCode
                            .NO_SUCH_CERTIFICATE_FOUND
                            .getErrorCode(), "id= " + cartItem.getIdCertificate()));
            OrderItem orderItem = new OrderItem(cartItem);
            orderItem.setPriceOfCertificate(certificate.getPrice());
            order.add(orderItem);
        }
        orderDAO.save(order);
        System.out.println(mapper.toDTO(order));
        return mapper.toDTO(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDto> find(Pageable pageable, OrderCriteriaInfo criteriaInfo) {
        Page<Order> all = orderDAO.findAll(criteriaInfo, pageable);
        return all.map(mapper::toDTO);
    }

    @Transactional
    @Override
    public OrderDto create(OrderDto orderReadDto) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public boolean delete(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public OrderDto update(OrderDto orderReadDto) {
        throw new UnsupportedOperationException();
    }
}
