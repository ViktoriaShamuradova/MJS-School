package com.epam.esm.persistence.impl;

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
    private static final String SQL_QUERY_READ_ONE_TAG_BY_NAME = "SELECT * FROM tags WHERE name = ?;";
    private static final String SQL_QUERY_READ_ONE_TAG_BY_ID = "SELECT * FROM tags WHERE id = ?;";
    private static final String SQL_QUERY_INSERT_TAG = "INSERT into tags (name) VALUES (?);";
    private static final String SQL_QUERY_DELETE_TAG_BY_NAME = "DELETE FROM tags WHERE name = ?;";
    private static final String SQL_QUERY_DELETE_TAG_BY_ID = "DELETE FROM tags WHERE id = ?;";
    private static final String SQL_QUERY_READ_TAG_LIST_BY_CERTIFICATE_ID =
            "SELECT id, name FROM tags t " +
                    "INNER JOIN certificate_tag ct " +
                    "ON t.id =ct.id_tag WHERE ct.id_certificate=?;";

    private final JdbcTemplate template;

    @Autowired
    public TagDAOImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Optional<Tag> find(String name) {
        return template.query(SQL_QUERY_READ_ONE_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name)
                .stream().findAny();
    }

    @Override
    public void delete(String name) {
        template.update(SQL_QUERY_DELETE_TAG_BY_NAME, name);
    }

    @Override
    public void create(Tag tag) {
        template.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_QUERY_INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        });
    }

    @Override
    public List<Tag> findAll() {
        return template.query(SQL_QUERY_READ_TAG_LIST
                , new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public List<Tag> findByCertificateId(long id) {
        return template.query(SQL_QUERY_READ_TAG_LIST_BY_CERTIFICATE_ID
                , new BeanPropertyRowMapper<>(Tag.class), id);
    }

    @Override
    public Optional<Tag> find(Long id) {
        return template.query(SQL_QUERY_READ_ONE_TAG_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny();
    }

    @Override
    public void delete(Long id) {
        template.update(SQL_QUERY_DELETE_TAG_BY_ID, id);
    }

    @Override
    public void update(Tag tag) {

    }
}
