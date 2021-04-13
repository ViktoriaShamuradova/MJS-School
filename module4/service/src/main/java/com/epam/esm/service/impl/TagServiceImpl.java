package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDao;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.modelmapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDAO;
    private final TagMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> find(PageInfo pageInfo, TagCriteriaInfo criteriaInfo) {
        return tagDAO.findAll(criteriaInfo, PageRequest.of(pageInfo.getCurrentPage() - 1, pageInfo.getLimit()))
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagDTO create(TagDTO tag) {
        TagCriteriaInfo tagCriteriaInfo = new TagCriteriaInfo();
        tagCriteriaInfo.setName(tag.getName());
        Optional<Tag> tagOptional = tagDAO.find(tagCriteriaInfo);
        if (tagOptional.isPresent()) {
            return mapper.toDTO(tagOptional.get());
        } else {
            tagDAO.save(mapper.toEntity(tag));
            return tag;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TagDTO findById(Long id) {
        return mapper.toDTO(tagDAO.findById(id).orElseThrow(() -> new NoSuchResourceException("id= " + id)));
    }

    @Override
    @Transactional
    public long getCount() {
        return tagDAO.getCount();
    }

    @Override
    @Transactional(readOnly = true)
    public TagDTO find(String name) {
        TagCriteriaInfo tagCriteriaInfo = new TagCriteriaInfo();
        tagCriteriaInfo.setName(name);

        return mapper.toDTO(tagDAO.find(tagCriteriaInfo).orElseThrow(() -> new NoSuchResourceException("name= " + name)));
    }

    @Override
    @Transactional
    public boolean delete(String name) {
        TagCriteriaInfo tagCriteriaInfo = new TagCriteriaInfo();
        tagCriteriaInfo.setName(name);
        Optional<Tag> tag = tagDAO.find(tagCriteriaInfo);
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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long aLong) {
        throw new UnsupportedOperationException();
    }

    private List<TagDTO> getListTagDto(List<Tag> tags) {
        return tags
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
