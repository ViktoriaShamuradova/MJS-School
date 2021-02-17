package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.persistence.OrderDAO;
import com.epam.esm.persistence.constant.OrderTableColumnName;
import com.epam.esm.persistence.mappers.OrderMapper;
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
public class OrderDAOImpl implements OrderDAO {

    private String SQL_QUERY_INSERT_ORDER = "INSERT INTO mjs_school.order " +
            "(id_user, count, total_sum, create_date) " +
            "VALUES(:id_user, :count, :total_sum, :create_date);";
    private static final String SQL_QUERY_READ_ONE_ORDER_BY_ID = "SELECT * FROM mjs_school.order WHERE id =?;";
    private static final String SQL_QUERY_READ_ORDER_LIST_BY_USER_ID = "SELECT * FROM mjs_school.order WHERE id_user =?;";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public OrderDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Order> create(Order order) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(OrderTableColumnName.ID_USER, order.getUserId())
                .addValue(OrderTableColumnName.COUNT, order.getCount())
                .addValue(OrderTableColumnName.TOTAL_SUM, order.getTotalSum())
                .addValue(OrderTableColumnName.CREATE_DATE, order.getCreateDate().toEpochMilli());
        namedParameterJdbcTemplate.update(SQL_QUERY_INSERT_ORDER, parameterSource, generatedKeyHolder, new String[]{"id"});

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public List<Order> findAll(Long aLong, int limit) {
        return null;
    }

    @Override
    public Optional<Order> find(Long id) {
        return jdbcTemplate.query(SQL_QUERY_READ_ONE_ORDER_BY_ID, new OrderMapper(), id).stream().findAny();
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return jdbcTemplate.query(SQL_QUERY_READ_ORDER_LIST_BY_USER_ID
                , new OrderMapper(), userId);
    }

    @Override
    public void update(Order o) {

    }


    @Override
    public void delete(Long aLong) {

    }

}
