package com.employee.management.service.service;

import com.employee.management.service.dtos.DepartmentResponse;
import com.employee.management.service.feignclient.DepartmentFeignClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DepartmentClientService {

    @Autowired
    DepartmentFeignClient departmentFeignClient;

     void validateDepartment(Long departmentId) {
        try {
            DepartmentResponse department =
                    departmentFeignClient.getDepartmentById(departmentId);

            if (department == null || department.getId() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "Invalid response from Department Service"
                );
            }

        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Department not found: " + departmentId
            );
        }
    }
}
