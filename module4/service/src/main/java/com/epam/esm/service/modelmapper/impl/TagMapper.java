package com.epam.esm.service.modelmapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.modelmapper.AbstractModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TagMapper extends AbstractModelMapper<TagDto, Tag, Long> {

    public TagMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public Tag toEntity(TagDto dto) {
        return Objects.isNull(dto) ? null : super.getModelMapper().map(dto, Tag.class);
    }

    @Override
    public TagDto toDTO(Tag entity) {
        return Objects.isNull(entity) ? null : super.getModelMapper().map(entity, TagDto.class);
    }
}
