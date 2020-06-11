package com.internship.tabulaprocessing.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class QueryParameter {

  private int size = 15;
  private int page = 1;
  private String sortBy = "id";

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

  public Pageable getPageable() {
    return PageRequest.of(page - 1, size, Sort.by(sortBy).ascending());
  }
}
