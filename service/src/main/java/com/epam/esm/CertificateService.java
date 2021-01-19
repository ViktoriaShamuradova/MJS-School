package com.epam.esm;

import com.epam.esm.dto.CertificateDTO;

import java.util.List;

public interface CertificateService {
    public List<CertificateDTO> getAllCertificates();

    public void saveCertificate(CertificateDTO certificateDTO);

    public CertificateDTO getCertificate(int id);

    public void deleteCertificate(int id);

    public void update(CertificateDTO certificateDTO);

    public List<CertificateDTO> getCertificatesByTagId(int certificateId);

}
