package com.epam.esm.service.impl;

import com.epam.esm.dto.CartContext;
import com.epam.esm.dto.OrderReadDto;
import com.epam.esm.dto.OrderReadItemDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderItem;
import com.epam.esm.persistence.OrderDAO;
import com.epam.esm.persistence.OrderItemDAO;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.PageInfo;
import com.epam.esm.service.entitydtomapper.OrderDtoMapper;
import com.epam.esm.service.entitydtomapper.OrderItemMapper;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final OrderDtoMapper orderDtoMapper;
    private final CertificateService certificateService;
    private final OrderItemMapper orderItemMapper;

    @Autowired
    OrderServiceImpl(OrderDAO orderDAO, OrderDtoMapper orderDtoMapper,
                     OrderItemMapper orderItemMapper,
                     CertificateService certificateService, OrderItemDAO orderItemDAO) {
        this.orderDAO = orderDAO;
        this.orderDtoMapper = orderDtoMapper;
        this.certificateService = certificateService;
        this.orderItemMapper = orderItemMapper;
        this.orderItemDAO = orderItemDAO;
    }

    @Override
    public OrderReadDto find(Long id) {
        Order order = orderDAO.find(id).orElseThrow(() ->
                new NoSuchResourceException(ExceptionCode.NO_SUCH_ORDER_FOUND.getErrorCode(), "id= " + id));

        List<OrderItem> orderItems = orderItemDAO.findByOrderId(order.getId());

        Set<OrderReadItemDto> orderReadItemDtos = new HashSet<>();
        orderItems.forEach(orderItem -> {
          //  orderReadItemDtos.add(orderItemMapper.changeEntityToDto(orderItem, certificateService.find(orderItem.getCertificateId())));
        });

        return orderDtoMapper.changeEntityToDto(order, orderReadItemDtos);
    }

    @Transactional
    @Override
    public OrderReadDto create(CartContext cart) {
        Set<OrderItem> orderItems = new HashSet<>();
        cart.getCartItems().forEach(cartItem -> {
            orderItems.add(new OrderItem(cartItem));
        });

        Order o = new Order(cart.getUserId(), orderItems);
        o = orderDAO.create(o).get();

        Order finalO = o;
        orderItems.forEach(orderItem -> {
           // orderItem.setOrderId(finalO.getId());
            orderItem.setId(orderItemDAO.create(orderItem).get().getId());
        });

        Set<OrderReadItemDto> orderReadItemDtos = new HashSet<>();
        orderItems.stream().forEach(orderItem -> {
          //  orderReadItemDtos.add(orderItemMapper.changeEntityToDto(orderItem, certificateService.find(orderItem.getCertificateId())));
        });

        return orderDtoMapper.changeEntityToDto(o, orderReadItemDtos);
    }

    @Override
    public List<OrderReadDto> find(Map<String, String> params) {
        System.out.println(params);
        System.out.println(params.get("userId"));
        if(params.containsKey("userId")) {
            return findByUserId(Long.parseLong(params.get("userId")));
        }
        return null;
    }

    @Override
    public List<OrderReadDto> findByUserId(long userId) {
        List<Order> orders = orderDAO.findByUserId(userId);
        List<OrderReadDto> orderReadDtos = new ArrayList<>();
        orders.forEach(order -> {
            orderReadDtos.add(orderDtoMapper.changeEntityToDto(order, null));
        });
        return orderReadDtos;
    }


    @Override
    public OrderReadDto create(OrderReadDto orderReadDto) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public OrderReadDto update(OrderReadDto orderReadDto) {
        return null;
    }

    private int calcCount(Set<OrderItem> orderItems) {
        int totalCount = 0;
        for (OrderItem orderItem : orderItems) {
            totalCount = totalCount + orderItem.getCount();
        }
        return totalCount;
    }

    private BigDecimal calcTotalSum(Set<OrderItem> orderItems) {
        BigDecimal totalSum = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            totalSum = totalSum.add(orderItem.getPriceOfCertificate().multiply(BigDecimal.valueOf(orderItem.getCount())));
        }
        return totalSum;
    }


    @Override
    public List<OrderReadDto> findAll(PageInfo pageInfo) {
        return null;
    }


//    private Order formOrder(Long userId, Set<OrderItem> orderItems) {
//        Order order = new Order();
//        order.setUserId(userId);
//        order.setCreateDate(Instant.now());
//        order.setCount(calcCount(orderItems));
//        order.setTotalSum(calcTotalSum(orderItems));
//
//        return order;
//    }
//
//    private Set<OrderItem> formOrderItem(Set<CartItem> cartItems) {
//        return cartItems.stream().map(cartItem -> {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setCertificateId(cartItem.getCertificateDTO().getId());
//            orderItem.setPriceOfCertificate(cartItem.getCertificateDTO().getPrice());
//            orderItem.setCount(cartItem.getCount());
//            return orderItem;
//        }).collect(Collectors.toSet());
//    }


//        Set<OrderItem> orderItems = formOrderItem(cart.getCartItems());
//        Order order = formOrder(cart.getUserId(), orderItems);
//        order = orderDAO.create(order).get();
//
//        //аждому нужно присвоить ордер id
//        Order finalOrder = order;
//        orderItems.forEach(orderItem -> {
//            orderItem.setOrderId(finalOrder.getId());
//            orderItem.setId(orderItemDAO.create(orderItem).get().getId());
//        });
//
//        Set<OrderReadItemDto> orderReadItems = orderItems.stream().map(orderItem -> {
//            CertificateDTO certificateDTO = certificateService.find(orderItem.getCertificateId());
//            return orderItemMapper.changeEntityToDto(orderItem, certificateDTO);
//        }).collect(Collectors.toSet());
//
//        return orderDtoMapper.changeEntityToDto(order, orderReadItems);
}
