package com.demo.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.StudentManagement.dto.ResponseDTO;
import com.demo.StudentManagement.dto.StudentDTO;
import com.demo.StudentManagement.service.StudentService;
import com.demo.StudentManagement.util.VarList;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ResponseDTO responseDTO;



    //build POST rest api for Student
    @PostMapping(value = "/saveStudent")
    public ResponseEntity saveStudent(@RequestBody StudentDTO studentDTO){
        try{
            String res = studentService.saveStudent(studentDTO);
            if (res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("success");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else if (res.equals("04")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Student already registered.");
                responseDTO.setContent(studentDTO);
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



     // build PUT rest api for a Student
    @PutMapping(value = "/updateStudent")
    public ResponseEntity<ResponseDTO> updateStudent(@RequestBody StudentDTO studentDTO) {
        try {
            String res = studentService.updateStudent(studentDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Student updated successfully.");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Student not found.");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Failed to update student.");
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



    // GET: Get All Students
    @GetMapping("/getAllStudents")
    public ResponseEntity<ResponseDTO> getAllStudents() {
        try {
            List<StudentDTO> students = studentService.getAllStudents();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Students retrieved successfully.");
            responseDTO.setContent(students);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // GET: Get Student by ID
    @GetMapping("/getStudent/{id}")
    public ResponseEntity<ResponseDTO> getStudentById(@PathVariable int id) {
        try {
            StudentDTO studentDTO = studentService.getStudentById(id);
            if (studentDTO != null) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Student found.");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Student not found.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // GET: Get Student by Email
    @GetMapping("/getStudentByEmail/{email}")
    public ResponseEntity<ResponseDTO> getStudentByEmail(@PathVariable String email) {
        try {
            StudentDTO studentDTO = studentService.getStudentByEmail(email);
            if (studentDTO != null) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Student found.");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Student not found.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // GET: Get Students by Name
    @GetMapping("/getStudentsByName/{name}")
    public ResponseEntity<ResponseDTO> getStudentsByName(@PathVariable String name) {
        try {
            List<StudentDTO> students = studentService.getStudentsByName(name);
            if (!students.isEmpty()) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Students found.");
                responseDTO.setContent(students);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No students found with the given name.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // DELETE: Delete Student by ID
    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<ResponseDTO> deleteStudent(@PathVariable int id) {
        try {
            String res = studentService.deleteStudent(id);
            if (res.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Student deleted successfully.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Student not found.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
