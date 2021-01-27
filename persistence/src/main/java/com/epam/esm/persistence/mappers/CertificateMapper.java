package com.epam.esm.persistence.mappers;

import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.constant.CertificateTableColumnName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();

        certificate.setId(rs.getInt(CertificateTableColumnName.ID));
        certificate.setName(rs.getString(CertificateTableColumnName.NAME));
        certificate.setDescription(rs.getString(CertificateTableColumnName.DESCRIPTION));
        certificate.setPrice(rs.getBigDecimal(CertificateTableColumnName.PRICE));
        certificate.setDuration(rs.getInt(CertificateTableColumnName.DURATION));
        certificate.setCreateDate(Instant.ofEpochMilli(rs.getLong(CertificateTableColumnName.CREATE_DATE)));
        certificate.setUpdateLastDate(Instant.ofEpochMilli(rs.getLong(CertificateTableColumnName.UPDATE_LAST_DATE)));

        return certificate;
    }
}
