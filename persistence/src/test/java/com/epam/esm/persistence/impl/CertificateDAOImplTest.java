package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
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
        Assertions.assertThat(certificateDAO.findAll()).isNotNull();
        Assertions.assertThat(3).isEqualTo(certificateDAO.findAll().size());
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
        Assertions.assertThat(size + 1).isEqualTo(certificateDAO.findAll().size());
    }

    @Test
    void update_shouldUpdateCertificate() {
        Certificate c = new Certificate();
        c.setName("Certificate 10");
        c.setDescription("Desc");
        c.setDuration(10);
        c.setPrice(new BigDecimal(100));
        c.setId(1L);
        c.setUpdateLastDate(Instant.now());

        certificateDAO.update(c);
        Certificate actual = certificateDAO.find(1L).get();
        c.setCreateDate(actual.getCreateDate());
        Assertions.assertThat(c).isEqualTo(actual);
    }

    @Test
    public void delete_shouldDeleteCertificate() {
        int sizeActual = certificateDAO.findAll().size();
        certificateDAO.delete(1L);
        Assertions.assertThat(sizeActual - 1).isEqualTo(certificateDAO.findAll().size());
    }

    @AfterEach
    public void endTest() {
        embeddedDatabase.shutdown();
    }
}
