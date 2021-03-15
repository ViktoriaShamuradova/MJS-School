package com.epam.esm.criteria_info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class PageInfo {
    @Min(1)
    private int currentPage;
    @Min(1)
    @Max(10)
    private int limit;
    @Positive
    private int numberOfPages;
    private int offset;

    public PageInfo(int currentPage, int limit, int numberOfPages) {
        this.currentPage = currentPage;
        this.limit = limit;
        this.numberOfPages = numberOfPages;
    }

    public PageInfo() {
        currentPage = 1;
        limit = 3;
    }

    @Positive
    public int getCurrentPage() {
        return currentPage;
    }

    public int getOffset() {
        return (currentPage * limit) - limit;
    }

    @Positive
    public int getLimit() {
        return limit;
    }

    @Positive
    public int getNumberOfPages() {
        return numberOfPages;
    }
}
