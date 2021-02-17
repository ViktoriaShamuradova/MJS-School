package com.epam.esm.persistence.impl;

import com.epam.esm.entity.OrderItem;
import com.epam.esm.persistence.OrderItemDAO;
import com.epam.esm.persistence.constant.OrderItemTableColumnName;
import com.epam.esm.persistence.mappers.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderItemDAOImpl implements OrderItemDAO {

    private static final String SQL_QUERY_INSERT_ORDER_ITEM = "INSERT INTO order_item (id_certificate, count, id_order, price_certificate) "
            + "VALUES(:id_certificate, :count, :id_order, :price_certificate)";
    private static final String SQL_QUERY_READ_ONE_ORDER_ITEM = "SELECT * FROM order_item WHERE id =?;";
    private static final String SQL_QUERY_READ_ORDER_ITEM_LIST_BY_ORDER_ID = "SELECT * FROM order_item WHERE id_order=?;";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public OrderItemDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<OrderItem> create(OrderItem orderItem) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(OrderItemTableColumnName.ID_CERTIFICATE, orderItem.getCertificateId())
                .addValue(OrderItemTableColumnName.COUNT, orderItem.getCount())
                .addValue(OrderItemTableColumnName.ID_ORDER, orderItem.getOrderId())
                .addValue(OrderItemTableColumnName.PRICE_CERTIFICATE, orderItem.getPriceOfCertificate());
        namedParameterJdbcTemplate.update(SQL_QUERY_INSERT_ORDER_ITEM, parameterSource, generatedKeyHolder, new String[]{"id"});

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public List<OrderItem> findAll(Long aLong, int limit) {
        return null;
    }


    @Override
    public Optional<OrderItem> find(Long id) {
        return jdbcTemplate.query(SQL_QUERY_READ_ONE_ORDER_ITEM, new OrderItemMapper(), id).stream().findAny();
    }

    @Override
    public List<OrderItem> findByOrderId(long orderId) {
        return jdbcTemplate.query(SQL_QUERY_READ_ORDER_ITEM_LIST_BY_ORDER_ID
                , new OrderItemMapper(), orderId);
    }


    @Override
    public void update(OrderItem o) {

    }
    @Override
    public void delete(Long aLong) {

    }


}
