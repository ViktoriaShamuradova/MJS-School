package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagDAOImpl implements TagDAO {
    private static final String SQL_QUERY_READ_TAG_LIST = "SELECT * FROM tag WHERE id>? LIMIT ?;";
    private static final String SQL_QUERY_READ_ONE_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?;";
    private static final String SQL_QUERY_READ_ONE_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?;";
    private static final String SQL_QUERY_INSERT_TAG = "INSERT into tag (name) VALUES (?);";
    private static final String SQL_QUERY_DELETE_TAG_BY_NAME = "DELETE FROM tag WHERE name = ?;";
    private static final String SQL_QUERY_DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?;";
    private static final String SQL_QUERY_READ_TAG_LIST_BY_CERTIFICATE_ID =
            "SELECT id, name FROM tag t " +
                    "INNER JOIN certificate_tag ct " +
                    "ON t.id =ct.id_tag WHERE ct.id_certificate=?;";
    private final static String SELECT_USED_TAG = "select t.name, t.id, count(*) as count from mjs_school.tag t " +
            "join mjs_school.certificate_tag ct on t.id=ct.id_tag\n" +
            "join mjs_school.certificate c on c.id=ct.id_certificate\n" +
            "join mjs_school.order_item oi on oi.id_certificate=c.id\n" +
            "join mjs_school.order o on o.id=oi.id_order \n" +
            "where o.id_user=(select id_user from mjs_school.order group by id_user order by sum(total_sum) desc limit 1)\n" +
            "group by t.name order by count(*)  desc;";
    private static final String SQL_QUERY_COUNT = "SELECT count(*) FROM tag;";

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
    public Map<Tag, Integer> getTagsOfUserWithHighestCostOfOrders() {
        Map<Tag, Integer> tags = new HashMap<>();
        template.query(SELECT_USED_TAG, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    Tag t = new Tag();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    tags.put(t, rs.getInt("count"));
                }
            }
        });
        return tags;
    }

    @Override
    public long getCount() {
        return template.queryForObject(SQL_QUERY_COUNT, Long.class);
    }

    @Override
    public Optional<Tag> create(Tag tag) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_QUERY_INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, generatedKeyHolder);
        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public List<Tag> findAll(Long id, int limit) {
        return template.query(SQL_QUERY_READ_TAG_LIST, new BeanPropertyRowMapper<>(Tag.class), id, limit);

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
    public void update(Tag o) {

    }
}
