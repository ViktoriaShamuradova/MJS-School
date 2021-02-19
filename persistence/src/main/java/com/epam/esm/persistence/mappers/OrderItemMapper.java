package com.epam.esm.persistence.mappers;

import com.epam.esm.entity.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItem or = new OrderItem();
        or.setId(rs.getInt("id"));
        or.setCount(rs.getInt("count"));
       // or.setOrderId(rs.getInt("id_order"));
      //  or.setCertificateId(rs.getInt("id_certificate"));
        or.setPriceOfCertificate(rs.getBigDecimal("price_certificate"));

        return or;
    }
}
