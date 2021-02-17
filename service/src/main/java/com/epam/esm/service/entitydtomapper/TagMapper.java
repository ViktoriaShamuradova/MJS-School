package com.epam.esm.service.entitydtomapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

public interface TagMapper {

    Tag changeToEntity(TagDTO tagDTO);

    TagDTO changeToDto(Tag tag);
}
