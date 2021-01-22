package com.epam.esm.persistence.impl;

import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.entitydtomapper.CertificateDtoMapper;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class CertificateServiceImplTest {
//    @Mock
//    private CertificateDAO certificateDAO;
//    @Mock
//    private TagService tagService;
//    @Mock
//    private CertificateDtoMapper certificateDtoMapper;
//
//    @InjectMocks
//    private CertificateServiceImpl certificateService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        Whitebox.setInternalState(certificateService, "tagService", tagService);
//    }
//
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