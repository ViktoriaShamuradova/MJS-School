package com.epam.esm.impl;

import com.epam.esm.CertificateDAO;
import com.epam.esm.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
            "description = ?, price=?, duration=?, create_date=?, update_last_date=?;";
    private static final String SQL_QUERY_DELETE_CERTIFICATE_BY_ID = "delete from module2_certificates_tags.certificates where id = ?;";

    private static final String SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID = "SELECT id, name, price, description, duration, price, create_date, update_last_date  " +
            "FROM module2_certificates_tags.certificates c " +
            "inner join module2_certificates_tags.certificate_tag ct on c.id =ct.id_certificate where ct.id_tag=?;";
    private static final String SQL_QUERY_INSERT_ID_CERTIFICATE_AND_ID_TAG = "insert into module2_certificates_tags.certificate_tag " +
            "(id_certificate, id_tag) values(?,?); ";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Certificate> allCertificates() {
        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATES_LIST, new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public Certificate certificateById(int id) {
        return jdbcTemplate.query(SQL_QUERY_READ_ONE_CERTIFICATE_BY_ID,
                new BeanPropertyRowMapper<>(Certificate.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public void update(int id, Certificate certificate) {
        jdbcTemplate.update(SQL_QUERY_UPDATE_CERTIFICATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(), certificate.getDuration(),
                certificate.getCreateDate(), certificate.getUpdateLastDate());
    }

    @Override
    public int create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_QUERY_INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setInt(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            ps.setObject(5, certificate.getCreateDate());
            ps.setObject(6, certificate.getUpdateLastDate());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(SQL_QUERY_DELETE_CERTIFICATE_BY_ID, id);
    }

    @Override
    public List<Certificate> getCertificatesByTagId(int tagId) {
        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID
                , new BeanPropertyRowMapper<>(Certificate.class), tagId);
    }

    @Override
    public void addCertificateTag(int certificateId, int tagId) {
        jdbcTemplate.update(SQL_QUERY_INSERT_ID_CERTIFICATE_AND_ID_TAG, certificateId, tagId);
    }
}


