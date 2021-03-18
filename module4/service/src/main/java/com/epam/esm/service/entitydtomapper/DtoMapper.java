package com.epam.esm.service.entitydtomapper;

public interface DtoMapper<ENTITY, DTO> {

    ENTITY changeToEntity(DTO dto);

    DTO changeToDto(ENTITY e);
}
