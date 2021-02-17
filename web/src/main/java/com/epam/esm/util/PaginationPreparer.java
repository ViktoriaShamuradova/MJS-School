package com.epam.esm.util;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PaginationPreparer {
    private static final int FIRST_PAGE = 1;

    public Map<String, Long> preparePage(Map<String, String> params, long countOfEntity) {
        Map<String, Long> page = new HashMap<>();
        int currentPage = Integer.parseInt(params.get(Constant.CURRENT_PAGE));
        long limit = Long.parseLong(params.get(Constant.LIMIT));
        long pageCount = calculatePageCount(countOfEntity, limit);
        page.put(Constant.PAGES_NUMBER, pageCount);
        page.put(Constant.LIMIT, limit);
        page.put(Constant.CURRENT_PAGE, (long) currentPage);
        return page;
    }

    private long calculatePageCount(long countOfEntity, long limit) {
        long pageCount = (long) Math.ceil(countOfEntity * 1.0 / limit);
        return pageCount == 0 ? FIRST_PAGE : pageCount;

    }

    public List<Link> preparePaginationLinks(Object invocationModel, Map<String, String> params) {
        long currentPage = Long.parseLong(params.get(Constant.CURRENT_PAGE));
        long pageCount = Long.parseLong(params.get(Constant.PAGES_NUMBER));

        List<Link> links = new ArrayList<>();

        if (pageCount != 1 && currentPage != 1) {
            params.replace(Constant.CURRENT_PAGE, String.valueOf(1));
            links.add(linkTo(invocationModel).withRel(Constant.FIRST_PAGE));
        }
        if (currentPage != pageCount) {
            params.replace(Constant.CURRENT_PAGE, String.valueOf(currentPage + 1));
            links.add(linkTo(invocationModel).withRel(Constant.NEXT_PAGE));
        }
        if (currentPage != 1) {
            params.replace(Constant.CURRENT_PAGE, String.valueOf(currentPage - 1));
            links.add(linkTo(invocationModel).withRel(Constant.PREVIOUS_PAGE));
        }
        if (pageCount != 1 && currentPage != pageCount) {
            params.replace(Constant.CURRENT_PAGE, String.valueOf(pageCount));
            links.add(linkTo(invocationModel).withRel(Constant.LAST_PAGE));
        }
        return links;
    }

    private static class Constant {
        public static final String FIRST_PAGE = "first page";
        public static final String NEXT_PAGE = "next page";
        public static final String PREVIOUS_PAGE = "previous page";
        public static final String LAST_PAGE = "last page";
        private final static String CURRENT_PAGE = "current page";
        private final static String PAGES_NUMBER = "number of pages";
        private final static String LIMIT = "limit";
    }
}
