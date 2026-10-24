package com.employee.management.service.feignclient;


import com.employee.management.service.dtos.DepartmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-management-service")
public interface DepartmentFeignClient {

    @GetMapping("/api/departments/{id}")
    DepartmentResponse getDepartmentById(@PathVariable("id") Long id);
}
