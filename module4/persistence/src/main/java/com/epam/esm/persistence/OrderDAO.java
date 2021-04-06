package com.epam.esm.persistence;

import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderDAO extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

}
