package com.epam.esm.service.entitydtomapper;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface CertificateDtoMapper {
    Certificate changeDtoToCertificate(CertificateDTO dto);

    CertificateDTO changeCertificateToDto(Certificate certificate, List<Tag> tagList);
}
