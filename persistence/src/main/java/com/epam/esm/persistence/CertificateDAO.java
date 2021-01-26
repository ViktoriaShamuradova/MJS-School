package com.epam.esm.persistence;

import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDAO {

    List<Certificate> findAll();

    Optional<Certificate> find(long id);

    void create(Certificate certificate);

    void update(Certificate certificate);

    void delete(long id);

    List<Certificate> findByTagId(long tagId);

    List<Certificate> findByPartOfNameOrDescription(String partOfNameOrDescription);
}
