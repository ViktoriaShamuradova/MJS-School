package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import java.util.List;

public interface TagService extends CrudService<TagDTO, Long> {

    List<TagDTO> findByCertificateId(long id);

    TagDTO find(String name);

    void delete(String name);

    boolean isExist(TagDTO tag);

    List<TagDTO> getMostUsedTagOfUserWithHighestCostOfOrders();

    long getCount();
}
