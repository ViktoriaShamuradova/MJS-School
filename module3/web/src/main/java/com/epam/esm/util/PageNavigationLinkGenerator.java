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
    private Map<NavigatorPage, Link> links;
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

    Map<NavigatorPage, Link> getLinks() {
        int pageCount = calculatePageCount(entityCount, pageInfo.getLimit());
        int currentPage = pageInfo.getCurrentPage();

        addFirstPage(currentPage, pageCount);
        addNextPage(currentPage, pageCount);
        addPrevPage(currentPage, pageCount);
        addLastPage(currentPage, pageCount);

        return links;
    }

    private void addLastPage(int currentPage, int pageCount) {
        if (pageCount != 1 && currentPage != pageCount) {
            pageInfo.setCurrentPage(pageCount);
            Link link = buildLink();
            links.put(NavigatorPage.LAST_PAGE, link);
        }
    }

    private void addPrevPage(int currentPage, int numberOfPages) {
        if (currentPage != 1) {
            pageInfo.setCurrentPage(currentPage - 1);
            Link link = buildLink();
            links.put(NavigatorPage.PREVIOUS_PAGE, link);
        }
    }

    private void addNextPage(int currentPage, int numberOfPages) {
        if (currentPage != numberOfPages) {
            pageInfo.setCurrentPage(currentPage + 1);
            Link link = buildLink();
            links.put(NavigatorPage.NEXT_PAGE, link);
        }
    }

    private void addFirstPage(int currentPage, int numberOfPages) {
        if (numberOfPages != 1 && currentPage != 1) {
            pageInfo.setCurrentPage(1);
            Link link = buildLink();
            links.put(NavigatorPage.FIRST_PAGE, link);
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
                        + PageParametersConstant.LIMIT + pageInfo.getLimit())
                .build(true);
    }

    private int calculatePageCount(long entityCount, int limit) {
        int pageCount = (int) Math.ceil(entityCount * 1.0 / limit);
        return pageCount == 0 ? 1 : pageCount;
    }

    private static class PageParametersConstant {
        public static final String CURRENT_PAGE = "currentPage=";
        public static final String LIMIT = "&limit=";
    }

    private static class UrlConstant {
        public static final String SCHEME = "http";
        public static final int PORT = 3333;
        public static final String HOST = "localhost";
    }
}
