package com.internship.tabulacore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AccountDto {

  private int id;

  private String fullName;

  private String email;

  @JsonIgnore
  private String password;

  private LocalDateTime datetimeCreated;

  private LocalDateTime datetimeUpdated;

  private List<RoleDto> roles;
}
