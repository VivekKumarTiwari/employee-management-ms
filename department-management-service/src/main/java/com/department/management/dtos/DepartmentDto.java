package com.department.management.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentDto {
    private Long id;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
