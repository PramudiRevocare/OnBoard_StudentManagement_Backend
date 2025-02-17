package com.demo.StudentManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.StudentManagement.dto.DepartmentDTO;
import com.demo.StudentManagement.model.Department;
import com.demo.StudentManagement.repository.DepartmentRepository;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;


    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        Department department = modelMapper.map(departmentDTO, Department.class);
        Department savedDepartment = departmentRepository.save(department);
        return modelMapper.map(savedDepartment, DepartmentDTO.class);
    }



    public DepartmentDTO updateDepartment(int id, DepartmentDTO departmentDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            department.setName(departmentDTO.getName());
            department.setDescription(departmentDTO.getDescription());
            Department updatedDepartment = departmentRepository.save(department);
            return modelMapper.map(updatedDepartment, DepartmentDTO.class);
        } else {
            throw new RuntimeException("Department not found");
        }
    }
    

    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList.stream()
                .map(department -> modelMapper.map(department, DepartmentDTO.class))
                .collect(Collectors.toList());
    }


    public DepartmentDTO getDepartmentById(int id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.map(value -> modelMapper.map(value, DepartmentDTO.class))
        .orElseThrow(() -> new RuntimeException("Department not found"));
    }


    public void deleteDepartment(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        
        departmentRepository.delete(department);
    }

}
