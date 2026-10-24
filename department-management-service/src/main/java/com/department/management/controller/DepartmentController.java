package com.department.management.controller;

import com.department.management.dtos.DepartmentDto;
import com.department.management.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public DepartmentDto createDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentService.create(departmentDto);
    }

    @PutMapping("/{id}")
    public DepartmentDto updateDepartment(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        return departmentService.update(id, departmentDto);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        departmentService.delete(id);
        return "Department deleted successfully!";
    }

    @GetMapping("/{id}")
    public DepartmentDto getDepartmentById(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    @GetMapping
    public List<DepartmentDto> getAllDepartment() {
        return departmentService.getAll();
    }
}
