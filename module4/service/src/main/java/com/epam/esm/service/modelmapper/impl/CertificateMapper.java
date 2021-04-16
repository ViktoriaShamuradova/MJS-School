package com.epam.esm.service.modelmapper.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.modelmapper.AbstractModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CertificateMapper extends AbstractModelMapper<CertificateDto, Certificate, Long> {

    public CertificateMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public Certificate toEntity(CertificateDto dto) {
        return Objects.isNull(dto) ? null : super.getModelMapper().map(dto, Certificate.class);
    }

    @Override
    public CertificateDto toDTO(Certificate entity) {
        return Objects.isNull(entity) ? null : super.getModelMapper().map(entity, CertificateDto.class);
    }
}
