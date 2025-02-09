package com.demo.StudentManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.StudentManagement.dto.DepartmentDTO;
import com.demo.StudentManagement.model.Department;
import com.demo.StudentManagement.repository.DepartmentRepository;
import com.demo.StudentManagement.util.VarList;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;


    public String saveDepartment(DepartmentDTO departmentDTO) {
        if (departmentDTO.getId() != null && departmentRepository.existsById(departmentDTO.getId())) {
            return VarList.RSP_DUPLICATED; 
        }
 
        Department department = modelMapper.map(departmentDTO, Department.class);
        department.setId(null);  

        departmentRepository.save(department); 
        return VarList.RSP_SUCCESS; 
    }


    public String updateDepartment(DepartmentDTO departmentDTO) {
        Optional<Department> existingDepartment = departmentRepository.findById(departmentDTO.getId());

        if (existingDepartment.isPresent()) {
            Department updatedDepartment = modelMapper.map(departmentDTO, Department.class);
            departmentRepository.save(updatedDepartment);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND; 
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
        return department.map(value -> modelMapper.map(value, DepartmentDTO.class)).orElse(null);
    }




    public String deleteDepartment(int id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return VarList.RSP_SUCCESS; 
        } else {
            return VarList.RSP_NO_DATA_FOUND; 
        }
    }

}
