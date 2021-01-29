package com.epam.esm.service.entitydtomapper.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.entitydtomapper.CertificateDtoMapper;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public CertificateDTO changeCertificateToDto(Certificate certificate, List<Tag> tagList) {
        CertificateDTO dto = new CertificateDTO();
        dto.setId(certificate.getId());
        dto.setName(certificate.getName());
        dto.setDescription(certificate.getDescription());
        dto.setPrice(certificate.getPrice());
        dto.setDuration(dto.getDuration());
        dto.setCreateDate(certificate.getCreateDate());
        dto.setUpdateLastDate(certificate.getUpdateLastDate());
        dto.setTagList(tagList);
        return dto;
    }
}
