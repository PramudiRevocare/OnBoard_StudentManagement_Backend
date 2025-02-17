package com.demo.StudentManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.StudentManagement.service.DepartmentService;
import com.demo.StudentManagement.dto.DepartmentDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;



    //build POST rest api for Department
    @PostMapping(value = "/saveDepartment")
    public ResponseEntity<DepartmentDTO> saveDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO savedDepartment = departmentService.saveDepartment(departmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }


    // build PUT rest api for a Department
    @PutMapping("/updateDepartment/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable int id, @RequestBody DepartmentDTO departmentDTO) {
        try {
            DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);
            return ResponseEntity.ok(updatedDepartment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




    // GET: Get All Departments
    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        try{
            List<DepartmentDTO> departmentDTO = departmentService.getAllDepartments();
            return ResponseEntity.ok(departmentDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    // GET: Get Department by ID
    @GetMapping("/getDepartment/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable int id) {
        System.out.println("Received ID in Controller: " + id);
        try {
            DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(departmentDTO);
        } catch (RuntimeException e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



  // DELETE: Delete Department by ID
    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable int id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok("Department deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found");
        }
    }




    
    


}
