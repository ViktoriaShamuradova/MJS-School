package com.epam.esm.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class TagDAOImpl implements TagDAO {
    private static final String SQL_QUERY_READ_TAG_LIST = "Select * from module2_gift_certificate.tags;";
    private static final String SQL_QUERY_READ_TAG_LIST_BY_CERTIFICATE_ID =
            "SELECT id, name FROM module2_gift_certificate.tags t \n" +
                    "inner join module2_gift_certificate.gift_certificates_tags ct \n" +
                    "on t.id =ct.id_tags where ct.id_gift_certificates=?;";
    private static final String SQL_QUERY_READ_ONE_TAG = "select * from tag where id = ?;";
    private static final String SQL_QUERY_INSERT_TAG = "insert into tag (name) values (?);";
    private static final String SQL_QUERY_DELETE_TAG = "delete from tag where id = ?;";


    private final JdbcTemplate template;


    @Autowired
    public TagDAOImpl(JdbcTemplate template) {
        this.template = template;

    }

    @Override
    public List<Tag> findAllTagList() {
        return template.query(SQL_QUERY_READ_TAG_LIST
                , new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag findTagById(int id) {
        return template.query(SQL_QUERY_READ_ONE_TAG, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public int addNewTag(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_QUERY_INSERT_TAG);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        return (int) keyHolder.getKey();
    }

    @Override
    public void deleteTag(int id) {
        template.update(SQL_QUERY_DELETE_TAG, id);
    }

    @Override
    public List<Tag> findTagByCertificateId(int certificateId) {
       return template.query(SQL_QUERY_READ_TAG_LIST_BY_CERTIFICATE_ID
                , new BeanPropertyRowMapper<>(Tag.class));

    }
}
