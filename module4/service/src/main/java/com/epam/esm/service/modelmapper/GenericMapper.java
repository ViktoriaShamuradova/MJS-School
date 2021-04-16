package com.epam.esm.service.modelmapper;

import com.epam.esm.dto.EntityDto;
import com.epam.esm.entity.Entity;

import java.io.Serializable;

public interface GenericMapper<DTO extends EntityDto<ID, DTO>, ENTITY extends Entity<ID>, ID extends Serializable> {
    ENTITY toEntity(DTO dto);

    DTO toDTO(ENTITY entity);
}
