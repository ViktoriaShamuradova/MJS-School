package com.epam.esm.entitydtomapper.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.entitydtomapper.TagDtoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagDTOMapperImpl implements TagDtoMapper {

    @Override
    public List<Tag> changeCertificateDtoToTagList(CertificateDTO certificateDTO) {
        List<TagDTO> tagsDTO = certificateDTO.getTagList();

        return tagsDTO.stream().map(tag -> {
            return changeTagDtoToTag(tag);
        }).collect(Collectors.toList());
    }

    @Override
    public Tag changeTagDtoToTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());

        return tag;
    }

    @Override
    public TagDTO changeTagToTagDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());

        return tagDTO;
    }

    @Override
    public TagDTO changeTagToTagDTO(Tag tag, List<CertificateDTO> certificates) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        tagDTO.setCertificates(certificates);
        return tagDTO;
    }
}
