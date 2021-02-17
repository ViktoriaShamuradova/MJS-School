package com.epam.esm.service.entitydtomapper;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;

import java.util.Set;

public interface CertificateDtoMapper {
    Certificate changeDtoToCertificate(CertificateDTO dto);

    CertificateDTO changeCertificateToDto(Certificate certificate, Set<TagDTO> tags);
}
