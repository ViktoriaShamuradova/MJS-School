package com.epam.esm.criteria_info;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
public class PageInfo {

    @Positive
    @Min(value = 1, message = "page size must be greater than 1")
    private int currentPage;

    @Positive
    @Min(value = 1, message = "page size must be greater than 1")
    private int limit;

    private int offset;

    public PageInfo(int currentPage, int limit) {
        this.currentPage = currentPage;
        this.limit = limit;
    }

    public PageInfo() {
        currentPage = 1;
        limit = 3;
    }

    public int getOffset() {
        return (currentPage * limit) - limit;
    }
}
