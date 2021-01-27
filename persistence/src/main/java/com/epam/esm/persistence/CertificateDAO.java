package com.epam.esm.persistence;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDAO {

    List<Certificate> findAll();

    Optional<Certificate> find(long id);

    void create(Certificate certificate);

    void update(CertificateDTO certificateDTO);

    void delete(long id);

    List<Certificate> findByPartOfNameOrDescription(String partOfNameOrDescription);
}
