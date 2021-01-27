package com.epam.esm.persistence.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.persistence.constant.CertificateTableColumnName;
import com.epam.esm.persistence.mappers.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * class which makes queries CRUD operations on a certificate to the database
 */

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    private static final String SQL_QUERY_READ_CERTIFICATES_LIST = "SELECT * FROM certificates ORDER BY name;";
    private static final String SQL_QUERY_READ_ONE_CERTIFICATE_BY_ID = "SELECT * FROM certificates WHERE id =?;";
    private static final String SQL_QUERY_INSERT_CERTIFICATE = "INSERT INTO certificates " +
            "(name, description, price, duration, create_date, update_last_date) " +
            "VALUES(:name, :description, :price, :duration, :create_date, :update_last_date);";
    private static final String SQL_QUERY_DELETE_CERTIFICATE_BY_ID = "DELETE FROM certificates WHERE id = ?;";

    private static final String NAME_STORED_PROCEDURE_TO_FIND_BY_PART_OF_NAME_OR_DESCRIPTION = "find_certificate_by_part_of_name";

    private static final String NAME_OF_INPUT_PARAMETER_IN_STORED_PROCEDURE_TO_FIND_BY_PART_OF_NAME_OR_DESCRIPTION = "_part";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void update(CertificateDTO certificateDTO) {
        String query = buildQueryForUpdate(certificateDTO);
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(CertificateTableColumnName.ID, certificateDTO.getId())
                .addValue(CertificateTableColumnName.NAME, certificateDTO.getName())
                .addValue(CertificateTableColumnName.DESCRIPTION, certificateDTO.getDescription())
                .addValue(CertificateTableColumnName.PRICE, certificateDTO.getPrice())
                .addValue(CertificateTableColumnName.DURATION, certificateDTO.getDuration())
                .addValue(CertificateTableColumnName.UPDATE_LAST_DATE, certificateDTO.getUpdateLastDate().toEpochMilli());

        namedParameterJdbcTemplate.update(query, parameterSource);
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATES_LIST, new CertificateMapper());
    }

    @Override
    public Optional<Certificate> find(long id) {
        return jdbcTemplate.query(SQL_QUERY_READ_ONE_CERTIFICATE_BY_ID,
                new CertificateMapper(), id).stream().findAny();
    }

    @Override
    public void create(Certificate certificate) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(CertificateTableColumnName.NAME, certificate.getName())
                .addValue(CertificateTableColumnName.DESCRIPTION, certificate.getDescription())
                .addValue(CertificateTableColumnName.PRICE, certificate.getPrice())
                .addValue(CertificateTableColumnName.DURATION, certificate.getDuration())
                .addValue(CertificateTableColumnName.CREATE_DATE, certificate.getCreateDate().toEpochMilli())
                .addValue(CertificateTableColumnName.UPDATE_LAST_DATE, certificate.getUpdateLastDate().toEpochMilli());
        namedParameterJdbcTemplate.update(SQL_QUERY_INSERT_CERTIFICATE, parameterSource);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_QUERY_DELETE_CERTIFICATE_BY_ID, id);
    }

    @Override
    public List<Certificate> findByPartOfNameOrDescription(String partOfNameOrDescription) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName(NAME_STORED_PROCEDURE_TO_FIND_BY_PART_OF_NAME_OR_DESCRIPTION)
                .returningResultSet("certificates", new CertificateMapper());

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue(NAME_OF_INPUT_PARAMETER_IN_STORED_PROCEDURE_TO_FIND_BY_PART_OF_NAME_OR_DESCRIPTION, partOfNameOrDescription);

        Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
        return (List<Certificate>) simpleJdbcCallResult.get("certificates");

    }

    private String buildQueryForUpdate(CertificateDTO certificateDTO) {
        StringBuilder queryStart = new StringBuilder("UPDATE certificates SET ");
        if (certificateDTO.getName() != null) {
            queryStart.append("name= :name,");
        }
        if (certificateDTO.getDescription() != null) {
            queryStart.append(" description= :description,");
        }
        if (certificateDTO.getDuration() != 0) {
            queryStart.append(" duration= :duration,");
        }
        if (certificateDTO.getPrice() != null) {
            queryStart.append(" price= :price,");
        }
        queryStart.append(" update_last_date=:update_last_date WHERE id=:id ;");
        return new String(queryStart);
    }
}


