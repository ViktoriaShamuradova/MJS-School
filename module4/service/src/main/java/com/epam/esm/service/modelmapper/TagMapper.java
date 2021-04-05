package com.epam.esm.service.modelmapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TagMapper {

    private final ModelMapper modelMapper;

    public Tag toEntity(TagDTO dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Tag.class);
    }

    public TagDTO toDTO(Tag entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, TagDTO.class);
    }
}
