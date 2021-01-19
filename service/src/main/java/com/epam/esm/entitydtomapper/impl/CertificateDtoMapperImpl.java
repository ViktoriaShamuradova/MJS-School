package com.epam.esm.entitydtomapper.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entitydtomapper.CertificateDtoMapper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        if (dto.getCreateDate() != null && dto.getLastUpdateDate() != null) {
            long millisCreateDate = dto.getCreateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            certificate.setCreateDate(millisCreateDate);
            long millisUpdateDate = dto.getCreateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            certificate.setLastUpdateDate(millisUpdateDate);
        }

        return certificate;
    }

    @Override
    public CertificateDTO changeCertificateToDto(Certificate certificate, List<TagDTO> tagList) {
        CertificateDTO dto = new CertificateDTO();
        dto.setId(certificate.getId());
        dto.setName(certificate.getName());
        dto.setDescription(certificate.getDescription());
        dto.setPrice(certificate.getPrice());
        dto.setDuration(dto.getDuration());
        LocalDateTime timeCreate = Instant.ofEpochMilli(certificate.getCreateDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        dto.setCreateDate(timeCreate);
        LocalDateTime timeLast = Instant.ofEpochMilli(certificate.getLastUpdateDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        dto.setLastUpdateDate(timeLast);
        dto.setTagList(tagList);
        return dto;
    }
}
