package com.epam.esm.service.modelmapper;

import com.epam.esm.dto.EntityDto;
import com.epam.esm.entity.Entity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.io.Serializable;

@RequiredArgsConstructor
@Data
public abstract class AbstractModelMapper<DTO extends EntityDto<ID, DTO>, ENTITY extends Entity<ID>, ID extends Serializable>
        implements GenericMapper<DTO, ENTITY, ID> {

    private final ModelMapper modelMapper;

}
