package com.epam.esm.service.entitydtomapper.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.entitydtomapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapper implements DtoMapper<Tag, TagDTO> {

    @Override
    public Tag changeToEntity(TagDTO tagDTO) {
        return new Tag(tagDTO.getId(), tagDTO.getName());
    }

    @Override
    public TagDTO changeToDto(Tag tag) {
        TagDTO t = new TagDTO();
        t.setId(tag.getId());
        t.setName(tag.getName());
        return t;
    }
}
