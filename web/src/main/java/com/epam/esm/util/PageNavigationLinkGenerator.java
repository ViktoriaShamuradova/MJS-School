package com.epam.esm.util;

import com.epam.esm.criteria_info.PageInfo;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class PageNavigationLinkGenerator {
    private PageInfo pageInfo;
    private Map<String, Link> links;
    private long entityCount;
    private String path;

    PageNavigationLinkGenerator(PageInfo pageInfo, long entityCount, String path) {
        this.pageInfo = pageInfo;
        this.entityCount = entityCount;
        this.path = path;
        links = new HashMap<>();
    }

    private PageNavigationLinkGenerator() {
    }

    Map<String, Link> getLinks() {
        pageInfo.setNumberOfPages(calculatePageCount(entityCount, pageInfo.getLimit()));

        int currentPage = pageInfo.getCurrentPage();
        int numberOfPages = pageInfo.getNumberOfPages();

        addFirstPage(currentPage, numberOfPages);
        addNextPage(currentPage, numberOfPages);
        addPrevPage(currentPage, numberOfPages);
        addLastPage(currentPage, numberOfPages);

        return links;
    }

    private void addLastPage(int currentPage, int numberOfPages) {
        if (numberOfPages != 1 && currentPage != numberOfPages) {
            pageInfo.setCurrentPage(numberOfPages);
            Link link = buildLink();
            links.put(NavigatorConstant.LAST_PAGE, link);
        }
    }

    private void addPrevPage(int currentPage, int numberOfPages) {
        if (currentPage != 1) {
            pageInfo.setCurrentPage(currentPage - 1);
            Link link = buildLink();
            links.put(NavigatorConstant.PREVIOUS_PAGE, link);
        }
    }

    private void addNextPage(int currentPage, int numberOfPages) {
        if (currentPage != numberOfPages) {
            pageInfo.setCurrentPage(currentPage + 1);
            Link link = buildLink();
            links.put(NavigatorConstant.NEXT_PAGE, link);
        }
    }

    private void addFirstPage(int currentPage, int numberOfPages) {
        if (numberOfPages != 1 && currentPage != 1) {
            pageInfo.setCurrentPage(1);
            Link link = buildLink();
            links.put(NavigatorConstant.FIRST_PAGE, link);
        }
    }

    private Link buildLink() {
        UriComponents uriComponents = buildUriComponent();
        URI uri = uriComponents.toUri();
        return Link.of(uri.toString());
    }

    private UriComponents buildUriComponent() {
        return UriComponentsBuilder.newInstance()
                .scheme(UrlConstant.SCHEME)
                .host(UrlConstant.HOST)
                .port(UrlConstant.PORT)
                .path(path)
                .query(PageParametersConstant.CURRENT_PAGE + pageInfo.getCurrentPage()
                        + PageParametersConstant.LIMIT + pageInfo.getLimit()
                        + PageParametersConstant.NUMBER_OF_PAGES + pageInfo.getNumberOfPages())
                .build(true);
    }

    private int calculatePageCount(long entityCount, int limit) {
        int pageCount = (int) Math.ceil(entityCount * 1.0 / limit);
        return pageCount == 0 ? 1 : pageCount;
    }

    private static class PageParametersConstant {
        public static final String CURRENT_PAGE = "currentPage=";
        public static final String LIMIT = "&limit=";
        public static final String NUMBER_OF_PAGES = "&numberOfPages=";
    }

    private static class UrlConstant {
        public static final String SCHEME = "http";
        public static final int PORT = 3333;
        public static final String HOST = "localhost";
    }

    private static class NavigatorConstant {
        public static final String FIRST_PAGE = "first page";
        public static final String LAST_PAGE = "last page";
        public static final String NEXT_PAGE = "next page";
        public static final String PREVIOUS_PAGE = "previous page";
    }
}
