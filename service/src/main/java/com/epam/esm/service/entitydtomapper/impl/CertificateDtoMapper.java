package com.epam.esm.service.entitydtomapper.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.entitydtomapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CertificateDtoMapper implements DtoMapper<Certificate, CertificateDTO> {

    @Override
    public Certificate changeToEntity(CertificateDTO dto) {
        Certificate certificate = new Certificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        certificate.setDescription(dto.getDescription());
        certificate.setPrice(dto.getPrice());
        certificate.setDuration(dto.getDuration());
        certificate.setCreateDate(dto.getCreateDate());
        certificate.setUpdateLastDate(dto.getUpdateLastDate());

        Set<TagDTO> tagsDTO = dto.getTags();

        Set<Tag> tags = tagsDTO.stream()
                .map(tagDTO -> new Tag(tagDTO.getId(), tagDTO.getName()))
                .collect(Collectors.toSet());

        certificate.setTags(tags);
        return certificate;
    }

    @Override
    public CertificateDTO changeToDto(Certificate certificate) {
        CertificateDTO dto = new CertificateDTO();
        dto.setId(certificate.getId());
        dto.setName(certificate.getName());
        dto.setDescription(certificate.getDescription());
        dto.setPrice(certificate.getPrice());
        dto.setDuration(certificate.getDuration());
        dto.setCreateDate(certificate.getCreateDate());
        dto.setUpdateLastDate(certificate.getUpdateLastDate());

        Set<Tag> tags = certificate.getTags();
        Set<TagDTO> tagsDTO = new HashSet<>();
        TagMapper mapperImp = new TagMapper();
        tags.forEach(tag -> {
            tagsDTO.add(mapperImp.changeToDto(tag));
        });
        dto.setTags(tagsDTO);
        return dto;
    }
}
