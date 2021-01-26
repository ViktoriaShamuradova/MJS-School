package com.epam.esm.service.impl;

import com.epam.esm.persistence.CertificateTagDAO;
import com.epam.esm.service.CertificateTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateTagServiceImpl implements CertificateTagService {

    private final CertificateTagDAO certificateTagDAO;

    @Autowired
    CertificateTagServiceImpl(CertificateTagDAO certificateTagDAO) {
        this.certificateTagDAO = certificateTagDAO;
    }

    @Override
    public void add(long certificateId, long tagId) {
        certificateTagDAO.add(certificateId, tagId);
    }
}
