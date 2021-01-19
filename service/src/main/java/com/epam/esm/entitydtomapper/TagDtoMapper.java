package com.epam.esm.entitydtomapper;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDtoMapper {

    public List<Tag> changeCertificateDtoToTagList(CertificateDTO dto);
    public Tag changeTagDtoToTag(TagDTO tagDTO);
    public TagDTO changeTagToTagDTO(Tag tag, List<CertificateDTO> certificatesDTO);

}
