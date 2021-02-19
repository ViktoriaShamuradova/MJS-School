package com.epam.esm.util;

import com.epam.esm.service.PageInfo;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PaginationPreparer {
    private static final int FIRST_PAGE = 1;

    public PageInfo preparePage(PageInfo pageInfo, long countOfEntity) {
        PageInfo preparedPage = new PageInfo();
        preparedPage.setCurrentPage(pageInfo.getCurrentPage());
        preparedPage.setLimit(pageInfo.getLimit());
        preparedPage.setNumberOfPages(calculatePageCount(countOfEntity, pageInfo.getLimit()));

        return preparedPage;
    }

    private int calculatePageCount(long countOfEntity, int limit) {
        int pageCount = (int) Math.ceil(countOfEntity * 1.0 / limit);
        return pageCount == 0 ? FIRST_PAGE : pageCount;
    }

    public List<Link> preparePaginationLinks(Object invocationModel, PageInfo pageInfo) {
        int currentPage = pageInfo.getCurrentPage();
        int pageCount = pageInfo.getNumberOfPages();

        List<Link> links = new ArrayList<>();

        if (pageCount != 1 && currentPage != 1) {
            pageInfo.setCurrentPage(1);
            links.add(linkTo(invocationModel).withRel(Constant.FIRST_PAGE));
        }
        if (currentPage != pageCount) {
            pageInfo.setCurrentPage(currentPage + 1);
            links.add(linkTo(invocationModel).withRel(Constant.NEXT_PAGE));
        }
        if (currentPage != 1) {
            pageInfo.setCurrentPage(currentPage - 1);
            links.add(linkTo(invocationModel).withRel(Constant.PREVIOUS_PAGE));
        }
        if (pageCount != 1 && currentPage != pageCount) {
            pageInfo.setCurrentPage(pageCount);
            links.add(linkTo(invocationModel).withRel(Constant.LAST_PAGE));
        }
        return links;
    }

    private static class Constant {
        public static final String FIRST_PAGE = "first page";
        public static final String NEXT_PAGE = "next page";
        public static final String PREVIOUS_PAGE = "previous page";
        public static final String LAST_PAGE = "last page";
    }
}
