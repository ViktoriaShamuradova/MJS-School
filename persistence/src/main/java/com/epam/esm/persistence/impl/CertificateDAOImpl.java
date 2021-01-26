package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.persistence.mappers.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    private static final String SQL_QUERY_READ_CERTIFICATES_LIST = "SELECT * FROM certificates;";
    private static final String SQL_QUERY_READ_ONE_CERTIFICATE_BY_ID = "SELECT * FROM certificates WHERE id =?;";
    private static final String SQL_QUERY_INSERT_CERTIFICATE = "INSERT INTO certificates " +
            "(name, description, price, duration, create_date, update_last_date) " +
            "VALUES(:name, :description, :price, :duration, :create_date, :update_last_date);";
    private static final String SQL_QUERY_UPDATE_CERTIFICATE = "UPDATE certificates " +
            "SET name = IF(STRCMP (name, :name)!=0, :name, name), " +
            "description = IF(STRCMP (name, :description)!=0, :description, description), " +
            " price = IF(price!= :price, :price, price), " +
            " duration = IF(duration!=:duration, :duration, duration), " +
            " create_date = IF(create_date!=:create_date, :create_date, create_date), " +
            " update_last_date = :update_last_date " +
            " WHERE id = :id;";
    private static final String SQL_QUERY_DELETE_CERTIFICATE_BY_ID = "delete from module2_certificates_tags.certificates where id = ?;";

    private static final String SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID = "SELECT id, name, price, description, duration, price, create_date, update_last_date  " +
            "FROM module2_certificates_tags.certificates c " +
            "inner join module2_certificates_tags.certificate_tag ct on c.id =ct.id_certificate where ct.id_tag=?;";


    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void update(Certificate certificate) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", certificate.getId())
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration())
                .addValue("create_date", certificate.getCreateDate().getEpochSecond())
                .addValue("update_last_date", certificate.getUpdateLastDate().getEpochSecond());
        namedParameterJdbcTemplate.update(SQL_QUERY_UPDATE_CERTIFICATE, parameterSource);
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATES_LIST, new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public Optional<Certificate> find(long id) {
        return jdbcTemplate.query(SQL_QUERY_READ_ONE_CERTIFICATE_BY_ID,
                new CertificateMapper(), id).stream().findAny();
    }

    @Override
    public void create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration())
                .addValue("create_date", certificate.getCreateDate().getEpochSecond())
                .addValue("update_last_date", certificate.getUpdateLastDate().getEpochSecond());
        namedParameterJdbcTemplate.update(SQL_QUERY_INSERT_CERTIFICATE, parameterSource, keyHolder, new String[]{"id"});
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_QUERY_DELETE_CERTIFICATE_BY_ID, id);
    }

    @Override
    public List<Certificate> findByTagId(long tagId) {
        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID
                , new BeanPropertyRowMapper<>(Certificate.class), tagId);
    }

    @Override
    public List<Certificate> findByPartOfNameOrDescription(String partOfNameOrDescription) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("find_certificate_by_part_of_name");

        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_name", partOfNameOrDescription);
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
        System.out.println(simpleJdbcCallResult);
        return null;
    }
}


