package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification_builder.impl.TagSpecificationBuilder;
import com.epam.esm.service.entitydtomapper.impl.TagMapper;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.validate.PaginationValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDAO;
    @Mock
    private PaginationValidator paginationValidator;
    @Mock
    private TagSpecificationBuilder specificationBuilder;
    @Mock
    private TagMapper mapper;

    @Test
    public void find_shouldFindListOfTags() {
        //given
        List<Tag> tags = new ArrayList<>();
        Tag t = new Tag();
        tags.add(t);

        List<Specification> specifications = new ArrayList<>();
        TagCriteriaInfo criteriaInfo = new TagCriteriaInfo();
        PageInfo pageInfo = new PageInfo(1, 1, 1);

        when(specificationBuilder.build(any()))
                .thenReturn(specifications);
        when(tagDAO.findAll(any(), anyInt(), anyInt()))
                .thenReturn(tags);
        //when
        tagService.find(pageInfo, criteriaInfo);
        //then
        verify(paginationValidator).validate(pageInfo);
        verify(specificationBuilder).build(any());
        verify(tagDAO)
                .findAll(specifications, (int) pageInfo.getOffset(), (int) pageInfo.getLimit());
        verify(mapper).changeToDto(t);
    }

    @Test
    public void create_shouldCreate() {
        Optional<Tag> t = Optional.of(new Tag());
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("AA");
        when(tagDAO.find(anyString())).thenReturn(t);

        tagService.create(tagDTO);
        verify(tagDAO).find(tagDTO.getName());
    }

    @Test
    public void findById_shouldFind() {
        Tag t = new Tag();
        t.setId(1L);
        when(tagDAO.find(anyLong())).thenReturn(Optional.of(t));
        tagService.findById(t.getId());
        verify(tagDAO).find(t.getId());
        verify(mapper).changeToDto(t);
    }

    @Test
    public void findById_shouldThrowException() {
        when(tagDAO.find(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(()
                -> tagService.findById(anyLong()))
                .isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void getCount_shouldGet() {
        when(tagDAO.getCount()).thenReturn(1L);
        Assertions.assertThat(tagService.getCount() == 1L);
    }

    @Test
    public void findByString_shouldFind() {
        Tag t = new Tag();
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("AA");
        t.setName("AA");
        when(tagDAO.find(anyString())).thenReturn(Optional.of(t));
        when(mapper.changeToDto(t)).thenReturn(tagDTO);
        tagService.find(t.getName());
        verify(tagDAO).find(t.getName());
        verify(mapper).changeToDto(t);

        Assertions.assertThat(tagService.find(anyString()).equals(tagDTO));
    }

    @Test
    public void findByName_shouldThrowException() {
        when(tagDAO.find(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(()
                -> tagService.find(anyString()))
                .isInstanceOf(NoSuchResourceException.class);
    }

    @Test
    public void deleteByString_shouldDelete() {
        Tag t = new Tag();
        t.setName("A");
        Optional<Tag> optionalTag = Optional.of(t);
        when(tagDAO.find(anyString())).thenReturn(optionalTag);

        tagService.delete(t.getName());

        verify(tagDAO).find(t.getName());
        verify(tagDAO).delete(t);
        Assertions.assertThat(tagService.delete(anyString()) == true);
    }

    @Test
    public void getMostUsedTagOfUserWithHighestCostOfOrders_shouldGet() {
        Map<Tag, Integer> map = new HashMap<>();
        Tag t = new Tag();
        Tag t2 = new Tag();
        map.put(t, 1);
        map.put(t2, 2);

        List<Tag> tags = new ArrayList<>();
        tags.add(t2);

        when(tagDAO.getTagsOfUserWithHighestCostOfOrders()).thenReturn(map);

        tagService.getMostUsedTagOfUserWithHighestCostOfOrders();

        Assertions.assertThat(tagService.getMostUsedTagOfUserWithHighestCostOfOrders().size()
                == tags.size());
    }
    @Test
    public void update_shouldThrownException(){
        assertThatThrownBy(() -> tagService.update(new TagDTO()))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void delete_shouldThrownException(){
        assertThatThrownBy(() -> tagService.delete(1L))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
