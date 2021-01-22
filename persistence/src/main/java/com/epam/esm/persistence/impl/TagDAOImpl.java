package com.epam.esm.persistence.impl;

import com.epam.esm.persistence.TagDAO;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {
    private static final String SQL_QUERY_READ_TAG_LIST = "Select * from module2_certificates_tags.tags;";
    private static final String SQL_QUERY_READ_TAG_LIST_BY_CERTIFICATE_ID =
            "SELECT id, name FROM module2_certificates_tags.tags t " +
                    "inner join module2_certificates_tags.certificate_tag ct " +
                    "on t.id =ct.id_tag where ct.id_certificate=?;";
    private static final String SQL_QUERY_READ_ONE_TAG = "select * from module2_certificates_tags.tags where id = ?;";
    private static final String SQL_QUERY_INSERT_TAG = "insert into module2_certificates_tags.tags (name) values (?);";
    private static final String SQL_QUERY_DELETE_TAG = "delete from module2_certificates_tags.tags where id = ?;";
    private static final String SQL_QUERY_READ_TAG_BY_NAME = "select * from module2_certificates_tags.tags where name = ?";

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

    @Nullable
    @Override
    public Tag findTagById(long id) {
        return template.query(SQL_QUERY_READ_ONE_TAG, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public int addNewTag(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_QUERY_INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void deleteTag(long id) {
        template.update(SQL_QUERY_DELETE_TAG, id);
    }

    @Override
    public List<Tag> findTagByCertificateId(long certificateId) {
        return template.query(SQL_QUERY_READ_TAG_LIST_BY_CERTIFICATE_ID
                , new BeanPropertyRowMapper<>(Tag.class), certificateId);
    }

    @Nullable
    @Override
    public Tag findTagByName(String name) {
        return template.query(SQL_QUERY_READ_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name)
                .stream().findAny().orElse(null);
    }
}
