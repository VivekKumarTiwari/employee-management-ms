package com.employee.management.service.service;

import com.employee.management.service.dtos.DepartmentResponse;
import com.employee.management.service.feignclient.DepartmentFeignClient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Log4j2
public class DepartmentClientService {

    @Autowired
    DepartmentFeignClient departmentFeignClient;

    @CircuitBreaker(name = "departmentclient", fallbackMethod = "departmentFallback")
    @Retry(name = "departmentclient")
     void validateDepartment(Long departmentId) {
        try {
            log.info("Calling Department Service for ID: {}", departmentId);
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
    private void  departmentFallback(Long id, Throwable ex) {

        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Department Service is temporarily unavailable"
        );
    }
}
