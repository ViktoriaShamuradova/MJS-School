package com.epam.esm.impl;

import com.epam.esm.CertificateDAO;
import com.epam.esm.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    private static final String SQL_QUERY_READ_CERTIFICATES_LIST = "SELECT * FROM module2_gift_certificate.gift_certificates;";
    private static final String SQL_QUERY_READ_ONE_CERTIFICATE_BY_ID = "SELECT * FROM module2_gift_certificate.gift_certificates where id =?;";
    private static final String SQL_QUERY_INSERT_CERTIFICATE = "insert into module2_gift_certificate.gift_certificates " +
            "(name, description, price, duration, create_date, last_update_date) values(?,?,?,?,?,?);";
    private static final String SQL_QUERY_UPDATE_CERTIFICATE = "update module2_gift_certificate.gift_certificates set name=?," +
            "description = ?, price=?, duration=?, create_date=?, last_update_date=?;";
    private static final String SQL_QUERY_DELETE_CERTIFICATE_BY_ID = "delete from module2_gift_certificate.gift_certificates where id = ?;";

    private static final String SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID = "SELECT id, name, price, description, duration, price, create_date, last_update_date " +
            "FROM module2_gift_certificate.gift_certificates c " +
            "inner join module2_gift_certificate.gift_certificates_tags ct " +
            "on c.id =ct.id_gift_certificates where ct.id_tags=? ";


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
                certificate.getCreateDate(), certificate.getLastUpdateDate());

    }

    //atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    @Override
    public void create(Certificate certificate) {
        jdbcTemplate.update(SQL_QUERY_INSERT_CERTIFICATE, certificate.getName(), certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate());

    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(SQL_QUERY_DELETE_CERTIFICATE_BY_ID, id);
    }

    @Override
    public List<Certificate> getCertificatesByTagId(int tagId) {

        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID
                , new BeanPropertyRowMapper<>(Certificate.class));

    }

}


