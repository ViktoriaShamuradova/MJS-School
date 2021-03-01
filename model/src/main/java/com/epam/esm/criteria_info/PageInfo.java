package com.epam.esm.criteria_info;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public class PageInfo {
    @Min(1)
    private long currentPage;
    @Min(1)
    @Max(10)
    private long limit;
    @Positive
    private long numberOfPages;

    private int offset;

    public PageInfo() {
    }

    public PageInfo(long currentPage, long limit, long numberOfPages) {
        this.currentPage = currentPage;
        this.limit = limit;
        this.numberOfPages = numberOfPages;
    }

    @Positive
    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getOffset() {
        return (currentPage * limit) - limit;
    }

    @Positive
    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    @Positive
    public long getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(long numberOfPages) {
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
