package com.department.management.service;

import com.department.management.dtos.DepartmentDto;
import com.department.management.entity.Department;
import com.department.management.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<DepartmentDto> getAll() {
        return departmentRepository.findAll().stream().map(department -> modelMapper.map(department,DepartmentDto.class)).collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getById(Long id) {
      return   departmentRepository.findById(id).map(department -> modelMapper.map(department,DepartmentDto.class))
                .orElseThrow(()-> new RuntimeException("Department not found"));
    }

    @Override
    public DepartmentDto create(DepartmentDto departmentDto) {
        if (departmentRepository.existsByNameIgnoreCase(departmentDto.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Department name already exists"
            );
        }
        
        // Convert Dto to Entity
        Department department = modelMapper.map(departmentDto, Department.class);
        
        // Save Department data
        Department saveDepartment = departmentRepository.save(department);

        // Convert Entity to Dto
      return modelMapper.map(saveDepartment, DepartmentDto.class);
    }

    @Override
    public DepartmentDto update(Long id, DepartmentDto departmentDto) {
        
       Department existingDepartment =departmentRepository.findById(id).orElseThrow(()->new RuntimeException("Department not found"));
        if (!existingDepartment.getName().equalsIgnoreCase(departmentDto.getName())
                && departmentRepository.existsByNameIgnoreCase(departmentDto.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Department name already exists"
            );
        }
        // Convert dto to Entity
        Department department = modelMapper.map(departmentDto, Department.class);
        
        department.setId(existingDepartment.getId());

        Department updateDepartment = departmentRepository.save(department);

        return modelMapper.map(updateDepartment,DepartmentDto.class);
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}
