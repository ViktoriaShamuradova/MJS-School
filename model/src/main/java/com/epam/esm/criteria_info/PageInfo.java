package com.epam.esm.criteria_info;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public class PageInfo {
    @Min(1)
    private int currentPage;
    @Min(1)
    @Max(10)
    private int limit;
    @Positive
    private int numberOfPages;

    private int offset;

    public PageInfo() {
    }

    public PageInfo(int currentPage, int limit, int numberOfPages) {
        this.currentPage = currentPage;
        this.limit = limit;
        this.numberOfPages = numberOfPages;
    }

    @Positive
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getOffset() {
        return (currentPage * limit) - limit;
    }

    @Positive
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Positive
    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "currentPage=" + currentPage +
                ", limit=" + limit +
                ", numberOfPages=" + numberOfPages +
                ", offset=" + offset +
                '}';
    }
}
