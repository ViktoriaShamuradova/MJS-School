package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.service.entitydtomapper.impl.CertificateDtoMapper;
import com.epam.esm.service.exception.NoSuchResourceException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;
    @Mock
    private CertificateDAO certificateDAO;
    @Mock
    private CertificateDtoMapper certificateDtoMapper;

    @Test
    public void find_shouldReturnListOfCertificateDTO() {
        //given
        List<Certificate> certificates = new ArrayList<>();
        Certificate c = createCertificate();
        certificates.add(c);

        CertificateCriteriaInfo criteriaInfo = new CertificateCriteriaInfo();
        PageInfo pageInfo = new PageInfo(1, 1);

        when(certificateDAO.findAll(any(), any()))
                .thenReturn(certificates);
        //when
        certificateService.find(pageInfo, criteriaInfo);
        //then
        verify(certificateDAO)
                .findAll(pageInfo, criteriaInfo);
        verify(certificateDtoMapper).changeToDto(c);
    }

    @Test
    public void find_shouldReturnEmptyList() {
        when(certificateDAO.findAll(any(), any()))
                .thenReturn(Collections.emptyList());

        Assertions.assertThat(certificateService.find(new PageInfo(), new CertificateCriteriaInfo()))
                .isEmpty();
    }

    @Test
    public void create_shouldCreateCertificate() {
        CertificateDTO certificateDTO = createCertificateDTO();
        Certificate certificate = createCertificate();
        when(certificateDtoMapper.changeToEntity(any()))
                .thenReturn(certificate);
        when(certificateDAO.create(any()))
                .thenReturn(certificate.getId());
        //when
        certificateService.create(certificateDTO);
        //then
        Assertions.assertThat(certificateService.create(certificateDTO)
                .equals(certificateDTO));
    }

    @Test
    public void findById_shouldFind() {
        //given
        Certificate c = createCertificate();
        CertificateDTO certificateDTO = createCertificateDTO();
        when(certificateDAO.find(anyLong())).thenReturn(Optional.of(c));
        when(certificateDtoMapper.changeToDto(c))
                .thenReturn(certificateDTO);
        //when
        certificateService.findById(1L);
        //then
        verify(certificateDAO).find(1L);
        verify(certificateDtoMapper).changeToDto(c);
        Assertions.assertThat(certificateService.findById(1L)
                .equals(certificateDTO));
    }

    @Test
    public void findById_shouldThrownException() {
        when(certificateDAO.find(anyLong())).thenThrow(NoSuchResourceException.class);
        assertThatThrownBy(() -> certificateService.findById(anyLong()))
                .isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void update_shouldThrownException() {
        when(certificateDAO.find(anyLong()))
                .thenThrow(NoSuchResourceException.class);
        assertThatThrownBy(() -> certificateService.update(new CertificateUpdateDto(), anyLong()))
                .isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void update_shouldUpdate() {
        //given
        CertificateUpdateDto certificateUpdateDto = new CertificateUpdateDto();
        Certificate certificate = createCertificate();
        CertificateDTO certificateDTO = createCertificateDTO();
        when(certificateDAO.find(anyLong()))
                .thenReturn(Optional.of(certificate));
        when(certificateDtoMapper.changeToDto(certificate))
                .thenReturn(certificateDTO);
        //when
        certificateService.update(certificateUpdateDto, 1L);
        //then
        verify(certificateDAO).find(1L);
        verify(certificateDAO).update(certificate);
        verify(certificateDtoMapper).changeToDto(certificate);
        Assertions.assertThat(certificateService.update(certificateUpdateDto, 1L)
                .equals(certificateDTO));
    }

    @Test
    public void getCount_shouldGet() {
        when(certificateDAO.getCount()).thenReturn(1L);
        Assertions.assertThat(certificateService.getCount() == 1L);
    }

    @Test
    public void updateWithParameterCertificateDTO_shouldThrownException(){
        assertThatThrownBy(() -> certificateService.update(new CertificateDTO()))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    private Certificate createCertificate() {
        Certificate c = new Certificate();
        c.setId(1L);
        c.setName("Cert");
        c.setDescription("Desc");
        c.setDuration(10);
        c.setCreateDate(Instant.now());
        c.setUpdateLastDate(Instant.now());
        c.setPrice(new BigDecimal(10));
        return c;
    }

    private CertificateDTO createCertificateDTO() {
        CertificateDTO c = new CertificateDTO();
        c.setName("Cert");
        c.setDescription("Desc");
        c.setDuration(10);
        c.setCreateDate(Instant.now());
        c.setUpdateLastDate(Instant.now());
        c.setPrice(new BigDecimal(10));
        return c;
    }
}
