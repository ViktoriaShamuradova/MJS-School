package com.epam.esm.persistence.mappers;

import com.epam.esm.entity.Order;
import com.epam.esm.persistence.constant.OrderTableColumnName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt(OrderTableColumnName.ID));
        order.setCount(rs.getInt(OrderTableColumnName.COUNT));
        order.setTotalSum(rs.getBigDecimal(OrderTableColumnName.TOTAL_SUM));
        order.setCreateDate(Instant.ofEpochMilli(rs.getLong(OrderTableColumnName.CREATE_DATE)));
        order.setUserId(rs.getInt(OrderTableColumnName.ID_USER));

        return order;
    }
}
