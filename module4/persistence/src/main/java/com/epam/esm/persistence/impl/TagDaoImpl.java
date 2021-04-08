package com.epam.esm.persistence.impl;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDao;
import com.epam.esm.persistence.dataspecification.TagSpecification;
import com.epam.esm.persistence.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {
    private final TagRepository tagRepository;


    @Override
    public List<Tag> findAll(TagCriteriaInfo criteriaInfo, PageInfo pageInfo) {
        TagSpecification specification = new TagSpecification(criteriaInfo);
        return tagRepository.findAll(specification, PageRequest.of(pageInfo.getCurrentPage() - 1, pageInfo.getLimit()))
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Tag> find(TagCriteriaInfo criteriaInfo) {
        TagSpecification specification = new TagSpecification(criteriaInfo);
        return tagRepository.findOne(specification);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public long getCount() {
        return tagRepository.count();
    }

    @Override
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }

    @Override
    public Map<Tag, Integer> getTagsOfUserWithHighestCostOfOrders() {
       return tagRepository.getTagsOfUserWithHighestCostOfOrders();
    }
}
