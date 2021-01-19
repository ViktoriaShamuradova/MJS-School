package com.epam.esm.entitydtomapper;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface CertificateDtoMapper {
    public Certificate changeDtoToCertificate(CertificateDTO dto);

    public CertificateDTO changeCertificateToDto(Certificate certificate, List<TagDTO> tagList);
}
