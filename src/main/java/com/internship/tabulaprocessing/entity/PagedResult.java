package com.internship.tabulaprocessing.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedResult<T> {

    private List<T> elements;
    private int currentPage;
    private int numOfTotalPages;
}
