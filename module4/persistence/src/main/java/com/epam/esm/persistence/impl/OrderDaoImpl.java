package com.epam.esm.persistence.impl;

import com.epam.esm.criteria_info.OrderCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.entity.Order;
import com.epam.esm.persistence.OrderDao;
import com.epam.esm.persistence.dataspecification.OrderSpecification;
import com.epam.esm.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private final OrderRepository orderRepository;

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public long getCount() {
        return orderRepository.count();
    }

    @Override
    public List<Order> findAll(OrderCriteriaInfo criteriaInfo, PageInfo pageInfo) {
        OrderSpecification orderSpecification = new OrderSpecification(criteriaInfo);
        return orderRepository.findAll(orderSpecification, PageRequest.of(pageInfo.getCurrentPage()-1, pageInfo.getLimit()))
                .stream()
                .collect(Collectors.toList());
    }
}
