package com.epam.esm.service;

import com.epam.esm.criteria_info.CriteriaInfo;
import com.epam.esm.dto.TagDTO;
import java.util.List;

public interface TagService extends CrudService<TagDTO, Long, CriteriaInfo> {

    TagDTO find(String name);

    boolean delete(String name);

    List<TagDTO> getMostUsedTagOfUserWithHighestCostOfOrders();

    long getCount();
}
