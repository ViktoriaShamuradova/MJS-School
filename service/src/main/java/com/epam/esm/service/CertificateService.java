package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;

import java.util.List;

public interface CertificateService {
    List<CertificateDTO> getAllCertificates();

    void saveCertificate(CertificateDTO certificateDTO);

    CertificateDTO getCertificate(int id);

    void deleteCertificate(int id);

    void update(CertificateDTO certificateDTO, long certificateId);

    List<CertificateDTO> getCertificatesByTagId(long tagId);

    List<CertificateDTO> getCertificatesByPartOfNameOrDescription(CertificateDTO certificateDTO);
}
