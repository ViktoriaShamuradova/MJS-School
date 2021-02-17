package com.epam.esm.persistence.impl;

import com.epam.esm.entity.User;
import com.epam.esm.persistence.UserDAO;
import com.epam.esm.persistence.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * class which makes queries CRUD operations on a user to the database
 */

@Repository
public class UserDAOImpl implements UserDAO {

    private static final String SQL_QUERY_READ_USERS_LIST = "SELECT * FROM user WHERE id>? LIMIT ?;";
    private static final String SQL_QUERY_READ_ONE_USER_BY_ID = "SELECT * FROM user WHERE id =?;";
    private static final String SQL_QUERY_COUNT="SELECT count(*) FROM user;";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<User> findAll(Long id, int limit) {
        return jdbcTemplate.query(SQL_QUERY_READ_USERS_LIST, new UserMapper(),id, limit);
    }

    @Override
    public Optional<User> find(Long id) {
        return jdbcTemplate.query(SQL_QUERY_READ_ONE_USER_BY_ID,
                new UserMapper(), id).stream().findAny();
    }

    @Override
    public void delete(Long aLong) {
    }

    @Override
    public void update(User o) {
    }

    @Override
    public Optional<User> create(User o) {
        return Optional.empty();
    }

    @Override
    public long getCount() {
        return jdbcTemplate.queryForObject(SQL_QUERY_COUNT, Long.class);
    }
}
