package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.CriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification_builder.impl.TagSpecificationBuilder;
import com.epam.esm.service.TagService;
import com.epam.esm.service.entitydtomapper.impl.TagMapper;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.NotSupportedException;
import com.epam.esm.service.validate.PaginationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final TagMapper mapper;
    private final PaginationValidator paginationValidator;
    private final TagSpecificationBuilder specificationBuilder;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, TagMapper mapper,
                          PaginationValidator paginationValidator,
                          TagSpecificationBuilder specificationBuilder) {
        this.mapper = mapper;
        this.tagDAO = tagDAO;
        this.paginationValidator = paginationValidator;
        this.specificationBuilder = specificationBuilder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> find(PageInfo pageInfo, CriteriaInfo criteriaInfo) {
        paginationValidator.validate(pageInfo);
        List<Specification> specifications = specificationBuilder.build(criteriaInfo);
        List<Tag> tags = tagDAO.findAll(specifications, pageInfo.getOffset(), pageInfo.getLimit());
        return getListTagDto(tags);
    }

    @Override
    @Transactional
    public TagDTO create(TagDTO tag) {
        Optional<Tag> tagOptional = tagDAO.find(tag.getName());
        if (tagOptional.isPresent()) {
            return mapper.changeToDto(tagOptional.get());
        } else {
            Long id = tagDAO.create(mapper.changeToEntity(tag));
            tag.setId(id);
            return tag;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TagDTO findById(Long id) {
        return mapper.changeToDto(tagDAO.find(id).orElseThrow(() -> new NoSuchResourceException("id= " + id)));
    }

    @Override
    @Transactional
    public long getCount() {
        return tagDAO.getCount();
    }

    @Override
    @Transactional(readOnly = true)
    public TagDTO find(String name) {
        return mapper.changeToDto(tagDAO.find(name).orElseThrow(() -> new NoSuchResourceException("name= " + name)));
    }

    @Override
    @Transactional
    public boolean delete(String name) {
        Optional<Tag> tag = tagDAO.find(name);
        if (tag.isPresent()) {
            tagDAO.delete(tag.get());
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public List<TagDTO> getMostUsedTagOfUserWithHighestCostOfOrders() {
        Map<Tag, Integer> tagsWithCount = tagDAO.getTagsOfUserWithHighestCostOfOrders();
        List<Tag> tags = new ArrayList<>();
        int maxValueInMap = Collections.max(tagsWithCount.values());
        for (Map.Entry<Tag, Integer> entry : tagsWithCount.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                tags.add(entry.getKey());
            }
        }
        return getListTagDto(tags);
    }

    @Override
    public TagDTO update(TagDTO tag) {
        throw new NotSupportedException(ExceptionCode.NOT_SUPPORTED_OPERATION.getErrorCode());
    }

    @Override
    public boolean delete(Long aLong) {
        throw new NotSupportedException(ExceptionCode.NOT_SUPPORTED_OPERATION.getErrorCode());
    }

    private List<TagDTO> getListTagDto(List<Tag> tags) {
        return tags
                .stream()
                .map(mapper::changeToDto)
                .collect(Collectors.toList());
    }
}
