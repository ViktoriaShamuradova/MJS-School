package com.epam.esm.persistence.impl;

import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.entity.Certificate;
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
public class CertificateDAOImpl implements CertificateDAO {

    private static final String SQL_QUERY_READ_CERTIFICATES_LIST = "SELECT * FROM module2_certificates_tags.certificates;";
    private static final String SQL_QUERY_READ_ONE_CERTIFICATE_BY_ID = "SELECT * FROM module2_certificates_tags.certificates where id =?;";
    private static final String SQL_QUERY_INSERT_CERTIFICATE = "insert into module2_certificates_tags.certificates " +
            "(name, description, price, duration, create_date, update_last_date) values(?,?,?,?,?,?);";
    private static final String SQL_QUERY_UPDATE_CERTIFICATE = "update module2_certificates_tags.certificates set name=?," +
            "description = ?, price=?, duration=?, create_date=?, update_last_date=? where id =?;";
    private static final String SQL_QUERY_DELETE_CERTIFICATE_BY_ID = "delete from module2_certificates_tags.certificates where id = ?;";

    private static final String SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID = "SELECT id, name, price, description, duration, price, create_date, update_last_date  " +
            "FROM module2_certificates_tags.certificates c " +
            "inner join module2_certificates_tags.certificate_tag ct on c.id =ct.id_certificate where ct.id_tag=?;";
    private static final String SQL_QUERY_INSERT_ID_CERTIFICATE_AND_ID_TAG = "insert into module2_certificates_tags.certificate_tag " +
            "(id_certificate, id_tag) values(?,?); ";
    private static final String FIRST_PART_OF_SQL_QUERY_READ_CERTIFICATES_BY_PART_OF_NAME_AND_DESCRIPTION =
            "select * from module2_certificates_tags.certificates " +
                    "where name like '%";
    private static final String SECOND_PART_OF_SQL_QUERY_READ_CERTIFICATES_BY_PART_OF_NAME_AND_DESCRIPTION = "%' or description like '%";
    private static final String THIRD_PART_OF_SQL_QUERY_READ_CERTIFICATES_BY_PART_OF_NAME_AND_DESCRIPTION = "%'";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Certificate> allCertificates() {
        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATES_LIST, new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Nullable
    @Override
    public Certificate certificateById(long id) {
        return jdbcTemplate.query(SQL_QUERY_READ_ONE_CERTIFICATE_BY_ID,
                new BeanPropertyRowMapper<>(Certificate.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public void update(long id, Certificate certificate) {
        jdbcTemplate.update(SQL_QUERY_UPDATE_CERTIFICATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(), certificate.getDuration(),
                certificate.getCreateDate(), certificate.getUpdateLastDate(), id);
    }

    @Override
    public long create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_QUERY_INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setObject(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            ps.setObject(5, certificate.getCreateDate().getEpochSecond());
            ps.setObject(6, certificate.getUpdateLastDate().getEpochSecond());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_QUERY_DELETE_CERTIFICATE_BY_ID, id);
    }

    @Override
    public List<Certificate> getCertificatesByTagId(long tagId) {
        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID
                , new BeanPropertyRowMapper<>(Certificate.class), tagId);
    }

    @Override
    public void addCertificateTag(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_QUERY_INSERT_ID_CERTIFICATE_AND_ID_TAG, certificateId, tagId);
    }

    @Override
    public List<Certificate> getCertificatesByPartOfNameOrDescription(String name, String description) {
        return jdbcTemplate.query(FIRST_PART_OF_SQL_QUERY_READ_CERTIFICATES_BY_PART_OF_NAME_AND_DESCRIPTION + name +
                        SECOND_PART_OF_SQL_QUERY_READ_CERTIFICATES_BY_PART_OF_NAME_AND_DESCRIPTION + description +
                        THIRD_PART_OF_SQL_QUERY_READ_CERTIFICATES_BY_PART_OF_NAME_AND_DESCRIPTION
                , new BeanPropertyRowMapper<>(Certificate.class));
    }
}


