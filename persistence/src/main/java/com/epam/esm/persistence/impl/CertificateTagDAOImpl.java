package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.CertificateTagDAO;
import com.epam.esm.persistence.mappers.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificateTagDAOImpl implements CertificateTagDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_QUERY_INSERT_ID_CERTIFICATE_AND_ID_TAG =
            "INSERT INTO certificate_tag " +
                    "(id_certificate, id_tag) VALUES(?,?); ";
    private static final String SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID =
            "SELECT id, name, price, description, duration, price, create_date, update_last_date  " +
                    "FROM certificates c INNER JOIN certificate_tag ct ON c.id =ct.id_certificate WHERE ct.id_tag=?;";
    private static final String SQL_QUERY_READ_TAG_LIST_BY_CERTIFICATE_ID =
            "SELECT id, name FROM tags t " +
                    "INNER JOIN certificate_tag ct " +
                    "ON t.id =ct.id_tag WHERE ct.id_certificate=?;";

    @Autowired
    public CertificateTagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_QUERY_INSERT_ID_CERTIFICATE_AND_ID_TAG, certificateId, tagId);
    }

    @Override
    public List<Certificate> findCertificateByTagId(long id) {
        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID
                , new CertificateMapper(), id);
    }

    @Override
    public List<Tag> findTagByCertificateId(long id) {
        return jdbcTemplate.query(SQL_QUERY_READ_TAG_LIST_BY_CERTIFICATE_ID
                , new BeanPropertyRowMapper<>(Tag.class), id);
    }
}
