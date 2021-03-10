package com.epam.esm.persistence;

import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.entity.Tag;

import java.util.Map;
import java.util.Optional;

public interface TagDAO extends CrudDAO<Tag, Long, TagCriteriaInfo> {

    Optional<Tag> find(String name);

    Map<Tag, Integer> getTagsOfUserWithHighestCostOfOrders();

    long getCount();
}
