package com.epam.esm;

import com.epam.esm.dto.CertificateDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CertificateService {
    List<CertificateDTO> getAllCertificates();

    void saveCertificate(CertificateDTO certificateDTO);

    CertificateDTO getCertificate(int id);

    void deleteCertificate(int id);

    @Transactional
    void update(CertificateDTO certificateDTO, int certificateId);

    List<CertificateDTO> getCertificatesByTagId(int tagId);

    //List<CertificateDTO> getCertificatesByPartOfNameOrDescription(String description);

    List<CertificateDTO> getCertificatesByPartOfNameOrDescription(CertificateDTO certificateDTO);
}
