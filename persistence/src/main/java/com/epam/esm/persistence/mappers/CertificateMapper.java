package com.epam.esm.persistence.mappers;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();

        certificate.setId(rs.getInt("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(rs.getBigDecimal("price"));
        certificate.setDuration(rs.getInt("duration"));
        certificate.setCreateDate(Instant.ofEpochMilli(rs.getLong("create_date")));
        certificate.setUpdateLastDate(Instant.ofEpochMilli(rs.getLong("create_date")));

        return certificate;
    }
}
