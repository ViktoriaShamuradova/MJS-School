package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.TagAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_shouldCreate() {
        Tag t = createTag();
        when(tagDAO.find(anyString())).thenReturn(Optional.empty());

        tagService.create(t);
        verify(tagDAO).find(t.getName());
    }

    @Test
    public void create_shouldThrowException() {
        Tag t = createTag();
        when(tagDAO.find(anyString())).thenReturn(Optional.of(t));
        assertThatThrownBy(() -> tagService.create(t)).isInstanceOf(TagAlreadyExistsException.class);
    }

    @Test
    public void find_shouldThrowException() {
        when(tagDAO.find(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> tagService.find(anyLong())).isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void find_shouldFind() {
        Tag t = createTag();
        when(tagDAO.find(anyLong())).thenReturn(Optional.of(t));
        tagService.find(t.getId());
        verify(tagDAO).find(t.getId());
    }

    @Test
    public void delete_shouldThrowException() {
        when(tagDAO.find(anyLong())).thenThrow(NoSuchResourceException.class);
        assertThatThrownBy(() -> tagService.delete(anyLong())).isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void delete_shouldDelete() {
        Tag t = createTag();
        when(tagDAO.find(anyLong())).thenReturn(Optional.of(t));

        tagService.delete(t.getId());

        verify(tagDAO).find(t.getId());
        verify(tagDAO).delete(t.getId());
    }

    @Test
    public void findByCertificateId_shouldFind() {
        List<Tag> tags = new ArrayList<>();
        tags.add(createTag());

        when(tagDAO.findByCertificateId(anyLong())).thenReturn(tags);
        tagService.findByCertificateId(anyLong());

        verify(tagDAO).findByCertificateId(anyLong());
    }

@Test
public void findByString_shouldThrowException() {
    when(tagDAO.find(anyString())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> tagService.find(anyString())).isInstanceOf(NoSuchResourceException.class);
}

    @Test
    public void findByString_shouldFind() {
        Tag t = createTag();
        when(tagDAO.find(anyString())).thenReturn(Optional.of(t));
        tagService.find(t.getName());
        verify(tagDAO).find(t.getName());
    }

    @Test
    public void deleteByString_shouldThrowException() {
        when(tagDAO.find(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> tagService.find(anyString())).isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void deleteByString_shouldDelete() {
        Tag t = createTag();
        when(tagDAO.find(anyString())).thenReturn(Optional.of(t));

        tagService.delete(t.getName());

        verify(tagDAO).find(t.getName());
        verify(tagDAO).delete(t.getName());
    }

    private Tag createTag() {
        Tag t = new Tag();
        t.setId(1L);
        t.setName("VV");
        return t;
    }
}
