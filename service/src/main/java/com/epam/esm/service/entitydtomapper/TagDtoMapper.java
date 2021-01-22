package com.epam.esm.service.entitydtomapper;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDtoMapper {

    List<Tag> changeCertificateDtoToTagList(CertificateDTO dto);

    Tag changeTagDtoToTag(TagDTO tagDTO);

    TagDTO changeTagToTagDTO(Tag tag, List<CertificateDTO> certificatesDTO);

    TagDTO changeTagToTagDTO(Tag tag);
}
