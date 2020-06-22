package com.internship.tabulaprocessing.database.service;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
import com.internship.tabulaprocessing.service.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

  @Mock private DepartmentRepository departmentRepository;

  @InjectMocks private DepartmentServiceImpl departmentService;

  @Test
  void findAll() {

    QueryParameter queryParameter = new QueryParameter();
    Mockito.when(departmentRepository.findAll(queryParameter.getPageable()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), queryParameter.getPageable(), 5));

    Page<Department> departmentList = departmentService.findAll(queryParameter.getPageable());
    assertTrue(departmentList.isEmpty());

    queryParameter.setSize(3);
    Mockito.when(departmentRepository.findAll(queryParameter.getPageable()))
        .thenReturn(
            new PageImpl<>(
                Arrays.asList(new Department(), new Department(), new Department()),
                queryParameter.getPageable(),
                queryParameter.getSize()));

    departmentList = departmentService.findAll(queryParameter.getPageable());
    assertTrue(departmentList.getSize() == queryParameter.getSize());
  }

  @Test
  void findById() {

    Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> departmentService.findById(1));

    Mockito.when(departmentRepository.findById(Mockito.anyInt()))
        .thenReturn(Optional.of(new Department()));
    Department department = departmentService.findById(1);
    assertNotNull(department);
  }

  @Test
  void findByName() {

    Mockito.when(departmentRepository.findByName(Mockito.any())).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> departmentService.findByName("test"));

    Mockito.when(departmentRepository.findByName(Mockito.any()))
        .thenReturn(Optional.of(new Department()));
    Department department = departmentService.findByName("test");
    assertNotNull(department);
  }

  @Test
  void persist() {

    Department department = new Department();
    department.setName("test");
    Mockito.when(departmentRepository.findByName(Mockito.any()))
        .thenReturn(Optional.of(new Department()));
    assertThrows(RuntimeException.class, () -> departmentService.persist(new Department()));
  }

  @Test
  void update() {

    Department department = new Department();
    department.setId(1);
    Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> departmentService.update(department,1));
  }

  @Test
  void delete() {

    Department department = new Department();
    department.setId(1);
    Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> departmentService.delete(department.getId()));
  }
}
