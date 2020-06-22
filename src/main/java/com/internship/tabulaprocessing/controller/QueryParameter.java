package com.internship.tabulaprocessing.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class QueryParameter {

    @Min(value = 1, message = "Page size cannot be less than 1")
    @Max(value = 100, message = "Page size cannot be greater than 100")
    private int size = 15;
    @Min(value = 1, message = "Page cannot be less than 1")
    private int page = 1;
    private String sortBy = "id";
    private boolean desc;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public Pageable getPageable() {
        return desc ? PageRequest.of(page - 1, size, Sort.by(sortBy).descending())
                : PageRequest.of(page - 1, size, Sort.by(sortBy).ascending());
    }
}
