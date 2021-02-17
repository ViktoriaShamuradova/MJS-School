package com.epam.esm.service.entitydtomapper.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.entitydtomapper.CertificateDtoMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CertificateDtoMapperImpl implements CertificateDtoMapper {
    @Override
    public Certificate changeDtoToCertificate(CertificateDTO dto) {
        Certificate certificate = new Certificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        certificate.setDescription(dto.getDescription());
        certificate.setPrice(dto.getPrice());
        certificate.setDuration(dto.getDuration());
        certificate.setCreateDate(dto.getCreateDate());
        certificate.setUpdateLastDate(dto.getUpdateLastDate());
        return certificate;
    }

    @Override
    public CertificateDTO changeCertificateToDto(Certificate certificate, Set<TagDTO> tags) {
        CertificateDTO dto = new CertificateDTO();
        dto.setId(certificate.getId());
        dto.setName(certificate.getName());
        dto.setDescription(certificate.getDescription());
        dto.setPrice(certificate.getPrice());
        dto.setDuration(certificate.getDuration());
        dto.setCreateDate(certificate.getCreateDate());
        dto.setUpdateLastDate(certificate.getUpdateLastDate());
        dto.setTags(tags);
        return dto;
    }
}
