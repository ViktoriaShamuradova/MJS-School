package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;

import java.util.List;

public interface CertificateService extends CrudService<CertificateDTO, Long> {
    List<CertificateDTO> findByPartOfNameOrDescription(String partOfNameOrDescription);

    List<CertificateDTO> findByTagId(long id);

    void addLinkCertificateWithTags (long certificateId, long tagId);
}

