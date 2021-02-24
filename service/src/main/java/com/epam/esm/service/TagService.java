package com.epam.esm.service;

import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.dto.TagDTO;
import java.util.List;

public interface TagService extends CrudService<TagDTO, Long, TagCriteriaInfo> {

    List<TagDTO> findByCertificateId(long id);

    TagDTO find(String name);

    boolean delete(String name);

    boolean isExist(TagDTO tag);

    List<TagDTO> getMostUsedTagOfUserWithHighestCostOfOrders();

    long getCount();
}
