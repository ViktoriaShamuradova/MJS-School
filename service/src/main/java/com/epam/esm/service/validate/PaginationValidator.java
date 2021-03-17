package com.epam.esm.service.validate;

import com.epam.esm.criteria_info.PageInfo;
import org.springframework.stereotype.Component;

@Component
public class PaginationValidator {
    private final int FIRST = 1;
    private final int CONSTANT_LIMIT = 3;

    public void validate(PageInfo pageInfo) {
        if (pageInfo == null) {
            pageInfo = new PageInfo();
            pageInfo.setCurrentPage(FIRST);
            pageInfo.setLimit(CONSTANT_LIMIT);
        }
        if (pageInfo.getCurrentPage() <= 0) {
            pageInfo.setCurrentPage(FIRST);
        }
        if (pageInfo.getLimit() <= 0) {
            pageInfo.setLimit(CONSTANT_LIMIT);
        }
    }
}
