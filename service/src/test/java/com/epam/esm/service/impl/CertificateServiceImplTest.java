package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.entitydtomapper.CertificateDtoMapper;
import com.epam.esm.service.exception.NoSuchResourceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

public class CertificateServiceImplTest {

//    @InjectMocks
//    private CertificateServiceImpl certificateService;
//
//    @Mock
//    private CertificateDAO certificateDAO;
//    @Mock
//    private TagService tagService;
//    @Mock
//    private CertificateDtoMapper certificateDtoMapper;
//    @Mock
//    private CertificateTagService certificateTagService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void shouldReturnListOfCertificates() {
//        when(certificateDAO.findAll()).thenReturn(Collections.emptyList());
//        when(certificateTagService.findTagByCertificateId(anyLong())).thenReturn(Collections.emptyList());
//        when(certificateDtoMapper.changeCertificateToDto(any(), any())).thenReturn(new CertificateDTO());
//
//        assertThat(certificateService.findAll()).isEmpty();
//    }
//
//    @Test
//    public void shouldThrownException() {
//        when(certificateDAO.findAll()).thenThrow(DataAccessException.class);
//
//        assertThatThrownBy(() -> certificateService.findAll()).isInstanceOf(DataAccessException.class);
//        //assertThatNullPointerException().isThrownBy(() -> certificateService.findAll());
//    }
//
//    @Test
//    public void shouldReturnCertificateDTO() {
//        when(certificateDAO.find(anyLong())).thenReturn(Optional.of(new Certificate()));
//        when(certificateDtoMapper.changeCertificateToDto(any(), any())).thenReturn(new CertificateDTO());
//
//        assertThat(certificateService.find(anyInt())).isInstanceOf(CertificateDTO.class);
//    }
//
//    @Test
//    public void shouldThrownNoSuchResourceException() {
//        when(certificateDAO.find(anyLong())).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> certificateService.find(1)).isInstanceOf(NoSuchResourceException.class);
//    }


//    @Test
//    public void testGetCertificate_certificateIsNull() {
//        when(certificateDAO.certificateById(anyInt())).thenReturn(null);
//
//        CertificateDTO result = certificateService.getCertificate(10);
//        Assert.isNull(result, "Result should be null");
//    }
//
//    @Test
//    public void testGetCertificate_certificateIsNotNull() {
//        when(certificateDAO.certificateById(anyInt())).thenReturn(new Certificate());
//        when(tagService.getTagsByCertificateId(anyInt())).thenReturn(Collections.emptyList());
//        when(certificateDtoMapper.changeCertificateToDto(any(), any())).thenReturn(new CertificateDTO());
//
//        CertificateDTO result = certificateService.getCertificate(10);
//        Assert.isTrue(result != null, "Result should not be null");
//    }
//
//    @Test
//    public void test() {
//        List<Tag> tags = new ArrayList<>();
//
//        assertTrue(tags.size() == 6);
//        assertTrue(tags.size() == 6);
//        assertTrue(tags.size() == 6);
//        assertTrue(tags.size() == 6);
//        assertTrue(tags.size() == 6);
//
//        assertThat(tags)
//                .hasSize(6)
//                .extracting(Tag::getId, Tag::getName);
//             //   .contains("a", "b", "c");
//        assertThat("a").isEqualTo("a");
//    }

    /**
     * public CertificateDTO getCertificate(int id) {
     *         Certificate certificate = certificateDAO.certificateById(id);
     *         if (certificate != null) {
     *             return certificateDtoMapper.changeCertificateToDto(certificate, tagService.getTagsByCertificateId(id));
     *         }
     *         return null;
     *     }
     */
}