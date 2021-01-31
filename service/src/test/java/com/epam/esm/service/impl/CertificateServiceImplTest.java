package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.entitydtomapper.CertificateDtoMapper;
import com.epam.esm.service.exception.NoSuchResourceException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Mock
    private CertificateDAO certificateDAO;
    @Mock
    private TagService tagService;
    @Mock
    private CertificateDtoMapper certificateDtoMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findByPartOfNameOrDescription_shouldReturnEmptyListOfCertificateDTO() {
        when(certificateDAO.findByPartOfNameOrDescription(any())).thenReturn(Collections.emptyList());
        when(tagService.findByCertificateId(anyLong())).thenReturn(Collections.emptyList());
        when(certificateDtoMapper.changeCertificateToDto(any(), any())).thenReturn(new CertificateDTO());

        Assertions.assertThat(certificateService.findByPartOfNameOrDescription(any())).isEmpty();
    }

    @Test
    public void findByPartOfNameOrDescription_shouldReturnListOfCertificateDTO() {
        // given
        List<Certificate> certificates = new ArrayList<>();
        Certificate c = createCertificate();
        certificates.add(c);
        when(certificateDAO.findByPartOfNameOrDescription("Cert")).thenReturn(certificates);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag());
        when(tagService.findByCertificateId(1L)).thenReturn(tags);

        // when
        certificateService.findByPartOfNameOrDescription("Cert");

        // then
        verify(certificateDAO).findByPartOfNameOrDescription("Cert");
        verify(tagService).findByCertificateId(1L);
        verify(certificateDtoMapper).changeCertificateToDto(c, tags);
    }

    @Test
    public void findByTagId_shouldReturnListOfCertificateDTO() {
        // given
        List<Certificate> certificates = new ArrayList<>();
        Certificate c = createCertificate();
        certificates.add(c);
        when(certificateDAO.findCertificateByTagId(anyLong())).thenReturn(certificates);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag());
        when(tagService.findByCertificateId(anyLong())).thenReturn(tags);

        // when
        certificateService.findByTagId(anyLong());

        // then
        verify(tagService).findByCertificateId(1L);
        verify(certificateDtoMapper).changeCertificateToDto(c, tags);
    }

    @Test
    public void addLinkCertificateWithTags_shouldInsertLink() {
        certificateService.addLinkCertificateWithTags(1L, 2L);
        verify(certificateDAO).addLinkCertificateWithTags(1L, 2L);
    }

    @Test
    public void findAll_shouldReturnListOfCertificateDTO() {
        // given
        List<Certificate> certificates = new ArrayList<>();
        Certificate c = createCertificate();
        certificates.add(c);
        when(certificateDAO.findAll()).thenReturn(certificates);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag());
        when(tagService.findByCertificateId(1L)).thenReturn(tags);

        // when
        certificateService.findAll();

        // then
        verify(certificateDAO).findAll();
        verify(tagService).findByCertificateId(1L);
        verify(certificateDtoMapper).changeCertificateToDto(c, tags);
    }

    @Test
    public void create_shouldCreate() {
        // given
        CertificateDTO cDTO = createCertificateDTO();

        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("BB");
        Tag tag2 = new Tag();
        tag2.setName("BA");
        tags.add(tag1);
        tags.add(tag2);

        cDTO.setTagList(tags);

        Certificate c = createCertificate();

        Tag newT = new Tag();
        newT.setName("BB");

        when(certificateDtoMapper.changeDtoToCertificate(cDTO)).thenReturn(c);
        when(tagService.create(tag1)).thenReturn(tag1);

        // when
        certificateService.create(cDTO);

        // then
        verify(certificateDtoMapper).changeDtoToCertificate(cDTO);
        verify(certificateDAO).create(c);
        verify(tagService).create(tag1);
        verify(certificateDAO).addLinkCertificateWithTags(1L, 1L);
    }

    @Test
    public void find_shouldFind() {
        //given
        Certificate c = createCertificate();
        List<Tag> tags = createListOfTags();
        when(certificateDAO.find(anyLong())).thenReturn(Optional.of(c));
        when(tagService.findByCertificateId(anyLong())).thenReturn(tags);
        //when
        certificateService.find(1L);
        //then
        verify(certificateDAO).find(1L);
        verify(tagService).findByCertificateId(c.getId());
        verify(certificateDtoMapper).changeCertificateToDto(c, tags);
    }

    @Test
    public void find_shouldThrownException() {
        when(certificateDAO.find(anyLong())).thenThrow(NoSuchResourceException.class);
        assertThatThrownBy(() -> certificateService.find(anyLong())).isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void delete_shouldThrowException() {
        when(certificateDAO.find(anyLong())).thenThrow(NoSuchResourceException.class);
        assertThatThrownBy(() -> certificateService.delete(anyLong())).isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void delete_shouldDelete() {
        Certificate c = createCertificate();
        when(certificateDAO.find(anyLong())).thenReturn(Optional.of(c));

        certificateService.delete(c.getId());

        verify(certificateDAO).find(c.getId());
        verify(certificateDAO).delete(c.getId());
    }

    @Test
    public void update_shouldThrownException() {
        when(certificateDAO.find(anyLong())).thenThrow(NoSuchResourceException.class);
        assertThatThrownBy(() -> certificateService.update(createCertificateDTO())).isInstanceOf(NoSuchResourceException.class);
    }
    @Test
    public void update_shouldUpdate() {
        CertificateDTO certificateDTO = createCertificateDTO();
        Certificate c = createCertificate();
        when(certificateDAO.find(anyLong())).thenReturn(Optional.of(c));
        certificateService.update(certificateDTO);
        verify(certificateDAO).find(certificateDTO.getId());
        verify(tagService, times(certificateDTO.getTagList().size())).create(any());
        verify(certificateDtoMapper).changeDtoToCertificate(certificateDTO);
        verify(certificateDAO).update(any());
    }


    private List<Tag> createListOfTags() {
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("BB");
        Tag tag2 = new Tag();
        tag2.setName("BA");
        tags.add(tag1);
        tags.add(tag2);
        return tags;
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
        c.setId(1L);
        c.setName("Cert");
        c.setDescription("Desc");
        c.setDuration(10);
        c.setCreateDate(Instant.now());
        c.setUpdateLastDate(Instant.now());
        c.setPrice(new BigDecimal(10));

        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("BB");
        Tag tag2 = new Tag();
        tag2.setName("BA");
        tags.add(tag1);
        tags.add(tag2);

        c.setTagList(tags);

        return c;
    }

}