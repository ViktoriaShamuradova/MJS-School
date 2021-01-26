package com.epam.esm.persistence.impl;

import com.epam.esm.persistence.CertificateTagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class CertificateTagDAOImpl implements CertificateTagDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_QUERY_INSERT_ID_CERTIFICATE_AND_ID_TAG = "INSERT INTO certificate_tag " +
            "(id_certificate, id_tag) VALUES(?,?); ";


    @Autowired
    public CertificateTagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_QUERY_INSERT_ID_CERTIFICATE_AND_ID_TAG, certificateId, tagId);
    }
}
