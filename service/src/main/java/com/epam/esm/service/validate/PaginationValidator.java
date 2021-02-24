package com.epam.esm.service.validate;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.service.constant.PageConstant;
import org.springframework.stereotype.Component;

@Component
public class PaginationValidator {
    private final int FIRST = 1;

    public void validate(PageInfo pageInfo) {
        if (pageInfo == null) {
            pageInfo = new PageInfo();
            pageInfo.setCurrentPage(FIRST);
            pageInfo.setLimit(PageConstant.LIMIT);
        }
        if (pageInfo.getCurrentPage() <= 0) {
            pageInfo.setCurrentPage(FIRST);
        }
        if (pageInfo.getLimit() <= 0) {
            pageInfo.setLimit(PageConstant.LIMIT);
        }
    }
}
