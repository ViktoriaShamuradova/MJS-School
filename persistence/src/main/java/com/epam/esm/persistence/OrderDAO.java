package com.epam.esm.persistence;

import com.epam.esm.criteria_info.OrderCriteriaInfo;
import com.epam.esm.entity.Order;

public interface OrderDAO extends CrudDAO<Order, Long, OrderCriteriaInfo> {

    long getCount();
}
