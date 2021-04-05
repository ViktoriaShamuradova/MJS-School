package com.epam.esm.service.modelmapper;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class CertificateMapper {

    private final ModelMapper modelMapper;

    public Certificate toEntity(CertificateDTO dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Certificate.class);
    }

    public CertificateDTO toDTO(Certificate entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, CertificateDTO.class);
    }
}
