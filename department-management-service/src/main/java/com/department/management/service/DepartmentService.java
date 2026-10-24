package com.department.management.service;

import com.department.management.dtos.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAll();

    DepartmentDto getById(Long id);

    DepartmentDto create(DepartmentDto departmentDto);

    DepartmentDto update(Long id, DepartmentDto departmentDto);

   void delete(Long id);
}
