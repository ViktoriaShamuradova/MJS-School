package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDAOImpl implements TagDAO {
    private static final String SQL_QUERY_READ_TAG_LIST = "SELECT * FROM tags;";

    private static final String SQL_QUERY_READ_ONE_TAG = "SELECT * FROM tags WHERE id = ?;";
    private static final String SQL_QUERY_INSERT_TAG = "INSERT into tags (name) VALUES (?);";
    private static final String SQL_QUERY_DELETE_TAG = "DELETE FROM tags WHERE id = ?;";
    private static final String SQL_QUERY_READ_TAG_BY_NAME = "SELECT * FROM tags WHERE name = ?";

    private final JdbcTemplate template;

    @Autowired
    public TagDAOImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Tag> findAll() {
        return template.query(SQL_QUERY_READ_TAG_LIST
                , new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> find(long id) {
        return template.query(SQL_QUERY_READ_ONE_TAG, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny();
    }

    @Override
    public void add(TagDTO tag) {
        template.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_QUERY_INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        });
    }

    @Override
    public void delete(long id) {
        template.update(SQL_QUERY_DELETE_TAG, id);
    }

    @Override
    public Optional<Tag> find(String name) {
        return template.query(SQL_QUERY_READ_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name)
                .stream().findAny();
    }
}
