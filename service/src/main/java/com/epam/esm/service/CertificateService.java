package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;

import java.util.List;

public interface CertificateService {
    List<CertificateDTO> findAll();

    void create(CertificateDTO certificateDTO);

    CertificateDTO find(int id);

    void delete(int id);

    void update(CertificateDTO certificateDTO);

    List<CertificateDTO> findByPartOfNameOrDescription(String partOfNameOrDescription);
}
