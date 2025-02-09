package com.demo.StudentManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.StudentManagement.service.DepartmentService;
import com.demo.StudentManagement.util.VarList;
import com.demo.StudentManagement.dto.DepartmentDTO;
import com.demo.StudentManagement.dto.ResponseDTO;
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

     @Autowired
    private ResponseDTO responseDTO;


    //build POST rest api for Department
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping(value = "/saveDepartment")
    public ResponseEntity saveDepartment(@RequestBody DepartmentDTO departmentDTO){
        try{
            String res = departmentService.saveDepartment(departmentDTO);
            if (res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("success");
                responseDTO.setContent(departmentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else if (res.equals("04")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Department already registered.");
                responseDTO.setContent(departmentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
                
            }else{
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }

        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
                responseDTO.setMessage(ex.getMessage());
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);


        }

    }


    // build PUT rest api for a Department
    @PutMapping(value = "/updateDepartment")
    public ResponseEntity<ResponseDTO> updateDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            String res = departmentService.updateDepartment(departmentDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Department updated successfully.");
                responseDTO.setContent(departmentDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } 
            else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Department not found.");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Failed to update Department.");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // GET: Get All Departments
    @GetMapping("/getAllDepartments")
    public ResponseEntity<ResponseDTO> getAllDepartments() {
        try {
            List<DepartmentDTO> departments = departmentService.getAllDepartments();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Departments retrieved successfully.");
            responseDTO.setContent(departments);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // GET: Get Department by ID
    @GetMapping("/getDepartment/{id}")
    public ResponseEntity<ResponseDTO> getDepartmentById(@PathVariable int id) {
        try {
            DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
            if (departmentDTO != null) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage(" Department found.");
                responseDTO.setContent(departmentDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage(" Department not found.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



  // DELETE: Delete Department by ID
    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<ResponseDTO> deleteDepartment(@PathVariable int id) {
        try {
            String res = departmentService.deleteDepartment(id);
            if (res.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Department deleted successfully.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Department not found.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    
    


}
