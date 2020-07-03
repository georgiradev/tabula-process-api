package com.internship.tabulaprocessing.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagedResult<T> {

  private List<T> elements;
  private int currentPage;
  private int numOfTotalPages;
  private long entitiesCount;
}
