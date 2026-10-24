package com.employee.management.service.service;
import com.employee.management.service.dtos.EmployeeDto;
import java.util.List;
public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employee);
    EmployeeDto updateEmployee(Long id, EmployeeDto employee);
    void deleteEmployee(Long id);
    EmployeeDto getEmployeeById(Long id);
    List<EmployeeDto> getAllEmployees();
}
