package com.epam.esm;

import com.epam.esm.dto.CertificateDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CertificateService {
    public List<CertificateDTO> getAllCertificates();

    public void saveCertificate(CertificateDTO certificateDTO);

    public CertificateDTO getCertificate(int id);

    public void deleteCertificate(int id);

    @Transactional
    void update(CertificateDTO certificateDTO, int certificateId);

    public List<CertificateDTO> getCertificatesByTagId(int tagId);

}
