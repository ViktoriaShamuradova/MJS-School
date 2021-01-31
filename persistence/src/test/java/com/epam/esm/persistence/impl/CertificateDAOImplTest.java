package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.persistence.mappers.CertificateMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

//expected= что реально получила, аctual - ак должно быть
//вынести в класс конфигурации
public class CertificateDAOImplTest {
    private EmbeddedDatabase embeddedDatabase;

    private JdbcTemplate jdbcTemplate;

    private CertificateDAO certificateDAO;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    public void setup() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create_db.sql")
                .addScript("insert.sql")
                .build();

        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        certificateDAO = new CertificateDAOImpl(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Test
    public void findAll_shouldFindAll() {
        int expectedSize = 3;
        Assertions.assertThat(certificateDAO.findAll()).isNotNull();
        Assertions.assertThat(certificateDAO.findAll().size()).isEqualTo(expectedSize);
    }

    @Test
    public void find_shouldFindCertificate() {
        Assertions.assertThat(certificateDAO.find(1l)).isNotNull();
    }

    @Test
    public void find_shouldOptionalEmpty() {
        Assertions.assertThat(certificateDAO.find(55L)).isEmpty();
    }

    @Test
    public void create_shouldCreateCertificate() {
        int size = certificateDAO.findAll().size();
        Certificate c = new Certificate();
        c.setName("Certificate 10");
        c.setDescription("Desc");
        c.setDuration(10);
        c.setPrice(new BigDecimal(100));
        c.setId(4L);
        c.setCreateDate(Instant.now());
        c.setUpdateLastDate(Instant.now());

        certificateDAO.create(c);
        int expectedSize = size + 1;
        Assertions.assertThat(certificateDAO.findAll().size()).isEqualTo(expectedSize);
    }

    @Test
    void update_shouldUpdateCertificate() {
        Certificate actual = new Certificate();
        actual.setName("Certificate 10");
        actual.setDescription("Desc");
        actual.setDuration(10);
        actual.setPrice(new BigDecimal(100));
        actual.setId(1L);
        actual.setUpdateLastDate(Instant.now());

        certificateDAO.update(actual);
        Certificate expected = certificateDAO.find(1L).get();
        actual.setCreateDate(expected.getCreateDate());
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void delete_shouldDeleteCertificate() {
        int sizeActual = certificateDAO.findAll().size();
        certificateDAO.delete(1L);
        int expectedSize = certificateDAO.findAll().size();
        Assertions.assertThat(sizeActual - 1).isEqualTo(expectedSize);
    }

    @Test
    public void addLinkCertificateWithTags_shouldAddLink() {
        certificateDAO.addLinkCertificateWithTags(1, 3);
        List<Certificate> certificates = certificateDAO.findCertificateByTagId(3);
        int expectedId = 1;
        for (Certificate actual : certificates) {
            if (actual.getId() == 1) {
                Assertions.assertThat(actual.getId()).isEqualTo(expectedId);
            }
        }
    }

    @Test
    public void findByPartOfNameOrDescription_shouldFind() {
        String partOfNameOrDescription = "Cert";
        String sqlQuery = "select id, name, description, duration, price, create_date, update_last_date " +
                "from certificates where name like '%" + partOfNameOrDescription + "%' or description like '%" + partOfNameOrDescription + "%';";

        List<Certificate> certificates = jdbcTemplate.query(sqlQuery, new CertificateMapper());
        int expectedSize = 3;
        Assertions.assertThat(certificates.size()).isEqualTo(expectedSize);
    }

    @Test
    public void findByPartOfNameOrDescription_shouldNotFind() {
        String partOfNameOrDescription = "aaaaaaa";
        String sqlQuery = "select id, name, description, duration, price, create_date, update_last_date " +
                "from certificates where name like '%" + partOfNameOrDescription + "%' or description like '%" + partOfNameOrDescription + "%';";

        List<Certificate> certificates = jdbcTemplate.query(sqlQuery, new CertificateMapper());
        int expectedSize = 0;
        Assertions.assertThat(certificates.size()).isEqualTo(expectedSize);
    }

    @Test
    public void findCertificateByTagId_shouldFind() {
        List<Certificate> certificates = certificateDAO.findCertificateByTagId(1);
        int expectedSize = 2;
        Assertions.assertThat(certificates.size()).isEqualTo(expectedSize);
    }

    @Test
    public void findCertificateByTagId_shouldNotFind() {
        List<Certificate> certificates = certificateDAO.findCertificateByTagId(4);
        int expectedSize = 0;
        Assertions.assertThat(certificates.size()).isEqualTo(expectedSize);
    }

    @AfterEach
    public void endTest() {
        embeddedDatabase.shutdown();
    }
}
