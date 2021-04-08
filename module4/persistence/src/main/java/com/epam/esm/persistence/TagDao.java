package com.epam.esm.persistence;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagDao {
    List<Tag> findAll(TagCriteriaInfo criteriaInfo, PageInfo pageInfo);
    Optional<Tag> find(TagCriteriaInfo criteriaInfo);
    Tag save(Tag tag);
    Optional<Tag> findById(Long id);
    long getCount();
    void delete(Tag tag);
    Map<Tag, Integer> getTagsOfUserWithHighestCostOfOrders();
}
