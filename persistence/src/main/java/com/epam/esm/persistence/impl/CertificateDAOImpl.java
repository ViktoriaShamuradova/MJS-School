package com.epam.esm.persistence.impl;

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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * class which makes queries CRUD operations on a certificate to the database
 */

@Repository
public class CertificateDAOImpl implements CertificateDAO {
    private static final String SQL_QUERY_UPDATE = "UPDATE certificate SET name=:name, description=:description,\" +\n" +
            "                \" price=:price, duration=:duration, last_update_date=:last_update_date WHERE id =:id;";
    private static final String SQL_QUERY_READ_CERTIFICATES_LIST = "SELECT * FROM certificate WHERE id>? ORDER BY name LIMIT ?;";
    private static final String SQL_QUERY_READ_ONE_CERTIFICATE_BY_ID = "SELECT * FROM certificate WHERE id =?;";
    private static final String SQL_QUERY_INSERT_CERTIFICATE = "INSERT INTO certificate " +
            "(name, description, price, duration, create_date, last_update_date) " +
            "VALUES(:name, :description, :price, :duration, :create_date, :last_update_date);";
    private static final String SQL_QUERY_DELETE_CERTIFICATE_BY_ID = "DELETE FROM certificate WHERE id = ?;";
    private static final String SQL_QUERY_DELETE_LINKS = "DELETE FROM certificate_tag WHERE id_certificate = ?;";
    private static final String SQL_QUERY_INSERT_ID_CERTIFICATE_AND_ID_TAG =
            "INSERT INTO certificate_tag " +
                    "(id_certificate, id_tag) VALUES(?,?); ";
    private static final String SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID =
            "SELECT id, name, price, description, duration, price, create_date, last_update_date  " +
                    "FROM certificate c INNER JOIN certificate_tag ct ON c.id =ct.id_certificate WHERE ct.id_tag=?;";
    private static final String NAME_STORED_PROCEDURE_TO_FIND_BY_PART_OF_NAME_OR_DESCRIPTION = "find_certificate_by_part_of_name";
    private static final String NAME_OF_INPUT_PARAMETER_IN_STORED_PROCEDURE_TO_FIND_BY_PART_OF_NAME_OR_DESCRIPTION = "_part";
    private static final String SQL_QUERY_COUNT = "SELECT count(*) FROM certificate;";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLinkCertificateWithTags(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_QUERY_INSERT_ID_CERTIFICATE_AND_ID_TAG, certificateId, tagId);
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

    @Override
    public List<Certificate> findByTagId(long id) {
        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATE_LIST_BY_TAG_ID
                , new CertificateMapper(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_QUERY_DELETE_CERTIFICATE_BY_ID, id);
    }

    @Override
    public void deleteAllLinksWithTags(long id) {
        jdbcTemplate.update(SQL_QUERY_DELETE_LINKS, id);
    }

    @Override
    public List<Certificate> findByTagNames(List<String> tagNames) {
        StringBuilder sql = new StringBuilder(
                "SELECT c.name, c.id, c. description, c.price, c.duration, c.create_date, c.last_update_date " +
                        "FROM certificate c JOIN certificate_tag ct ON c.id=ct.id_certificate\n" +
                        "JOIN tag t ON ct.id_tag=t.id GROUP BY c.name HAVING GROUP_CONCAT(t.name) LIKE :search ");
        String separator = (" AND GROUP_CONCAT(t.name) LIKE :search ");

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        for (int i = 0; i < tagNames.size(); i++) {
            String finalName = "%" + tagNames.get(i).trim() + "%";
            parameterSource.addValue("search", finalName);
            if (i == tagNames.size() - 1) {
                sql.append(";");
            } else {
                sql.append(separator);
            }
        }

        return namedParameterJdbcTemplate.query(sql.toString(), parameterSource, new CertificateMapper());
    }

    @Override
    public long getCount() {
        return jdbcTemplate.queryForObject(SQL_QUERY_COUNT, Long.class);
    }

    @Override
    public void update(Certificate certificate) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(CertificateTableColumnName.ID, certificate.getId())
                .addValue(CertificateTableColumnName.NAME, certificate.getName())
                .addValue(CertificateTableColumnName.DESCRIPTION, certificate.getDescription())
                .addValue(CertificateTableColumnName.PRICE, certificate.getPrice())
                .addValue(CertificateTableColumnName.DURATION, certificate.getDuration())
                .addValue(CertificateTableColumnName.LAST_UPDATE_DATE, certificate.getUpdateLastDate().toEpochMilli());

        namedParameterJdbcTemplate.update(SQL_QUERY_UPDATE, parameterSource);
    }

    @Override
    public Optional<Certificate> create(Certificate certificate) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(CertificateTableColumnName.NAME, certificate.getName())
                .addValue(CertificateTableColumnName.DESCRIPTION, certificate.getDescription())
                .addValue(CertificateTableColumnName.PRICE, certificate.getPrice())
                .addValue(CertificateTableColumnName.DURATION, certificate.getDuration())
                .addValue(CertificateTableColumnName.CREATE_DATE, certificate.getCreateDate().toEpochMilli())
                .addValue(CertificateTableColumnName.LAST_UPDATE_DATE, certificate.getUpdateLastDate().toEpochMilli());
        namedParameterJdbcTemplate.update(SQL_QUERY_INSERT_CERTIFICATE, parameterSource, generatedKeyHolder, new String[]{"id"});

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public List<Certificate> findAll(Long id, int limit) {
        return jdbcTemplate.query(SQL_QUERY_READ_CERTIFICATES_LIST, new CertificateMapper(), id, limit);
    }

    @Override
    public Optional<Certificate> find(Long id) {
        return jdbcTemplate.query(SQL_QUERY_READ_ONE_CERTIFICATE_BY_ID,
                new CertificateMapper(), id).stream().findAny();
    }
}
