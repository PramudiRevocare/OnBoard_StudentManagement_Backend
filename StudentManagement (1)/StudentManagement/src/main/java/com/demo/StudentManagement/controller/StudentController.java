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

import com.demo.StudentManagement.dto.CourseDTO;
import com.demo.StudentManagement.dto.DepartmentDTO;
import com.demo.StudentManagement.dto.StudentDTO;
import com.demo.StudentManagement.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;




    //build POST rest api for Student
    @PostMapping(value = "/saveStudent")
        public ResponseEntity<StudentDTO> saveStudent(@RequestBody StudentDTO studentDTO){
            StudentDTO savedStudent = studentService.saveStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }



    //  // build PUT rest api for a Student
    // @PutMapping(value = "/updateStudent")
    // public ResponseEntity<ResponseDTO> updateStudent(@RequestBody StudentDTO studentDTO) {
    //     try {
    //         String res = studentService.updateStudent(studentDTO);
    //         if (res.equals(VarList.RSP_SUCCESS)) {
    //             responseDTO.setCode(VarList.RSP_SUCCESS);
    //             responseDTO.setMessage("Student updated successfully.");
    //             responseDTO.setContent(studentDTO);
    //             return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    //         } 
    //         else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
    //             responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
    //             responseDTO.setMessage("Student not found.");
    //             responseDTO.setContent(null);
    //             return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    //         } 
    //         else {
    //             responseDTO.setCode(VarList.RSP_FAIL);
    //             responseDTO.setMessage("Failed to update student.");
    //             responseDTO.setContent(null);
    //             return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    //         }
    //     } 
    //     catch (Exception ex) {
    //         responseDTO.setCode(VarList.RSP_ERROR);
    //         responseDTO.setMessage("Error: " + ex.getMessage());
    //         responseDTO.setContent(null);
    //         return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }



    // GET: Get All Students
    @GetMapping("/getAllStudents")
        public ResponseEntity<List<StudentDTO>> getAllStudents() {
        try{
            List<StudentDTO> studentDTO = studentService.getAllStudents();
            return ResponseEntity.ok(studentDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    // GET: Get Student by ID
    @GetMapping("/getStudent/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable int id) {
        System.out.println("Received ID in Controller: " + id);
        try {
            StudentDTO studentDTO = studentService.getStudentById(id);
            return ResponseEntity.ok(studentDTO);
        } catch (RuntimeException e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    


    // GET: Get Students by Name
    @GetMapping("/getStudentsByName/{name}")
    public ResponseEntity<StudentDTO> getStudentsByName(@PathVariable String name) {
        System.out.println("Received Name in Controller: " + name);
        try {
            StudentDTO studentDTO = studentService.getStudentByName(name);
            return ResponseEntity.ok(studentDTO);
        } catch (RuntimeException e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    



    // DELETE: Delete Student by ID
    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
    }
    


}
