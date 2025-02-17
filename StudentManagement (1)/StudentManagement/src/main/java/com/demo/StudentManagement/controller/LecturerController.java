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
import com.demo.StudentManagement.dto.LecturerDTO;
import com.demo.StudentManagement.service.LecturerService;
import com.demo.StudentManagement.util.VarList;
import java.util.List;


@RestController
@RequestMapping("api/v1/lecturers")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;



    //build POST rest api for Lecturer
    @PostMapping(value = "/saveLecturer")
     public ResponseEntity<LecturerDTO> saveLecturer(@RequestBody LecturerDTO lecturerDTO) {
        LecturerDTO savedLecturer = lecturerService.saveLecturer(lecturerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLecturer);
    }



    //  // build PUT rest api for a Lecturer
    @PutMapping(value = "/updateLecturer/{id}")
    public ResponseEntity<LecturerDTO> updateLecturer(@PathVariable int id, @RequestBody LecturerDTO lecturerDTO) {
        System.out.println("Received ID in Controller: " + id);

        try {
            LecturerDTO updatedLecturer = lecturerService.updateLecturer(id, lecturerDTO);
            return ResponseEntity.ok(updatedLecturer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    // GET: Get All Lecturers
    @GetMapping("/getAllLecturers")
        public ResponseEntity<List<LecturerDTO>> getAllLecturers() {
        try{
            List<LecturerDTO> lecturerDTO = lecturerService.getAllLecturers();
            return ResponseEntity.ok(lecturerDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    // GET: Get lecturers by ID
    @GetMapping("/getLecturer/{id}")
    public ResponseEntity<LecturerDTO> getLecturerById(@PathVariable int id) {
        System.out.println("Received ID in Controller: " + id);
        try {
            LecturerDTO lecturerDTO = lecturerService.getLecturerById(id);
            return ResponseEntity.ok(lecturerDTO);
        } catch (RuntimeException e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }






    // DELETE: Delete Student by ID
    @DeleteMapping("/deleteLecturer/{id}")
    public ResponseEntity<String> deleteLecturer(@PathVariable int id) {
        try {
            lecturerService.deleteLecturer(id);
            return ResponseEntity.ok("Lecturer deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lecturer not found");
        }
    }


    // // Get all lecturers by department ID
    // @GetMapping("/department/{departmentId}")
    // public ResponseEntity<?> getLecturersByDepartment(@PathVariable Integer departmentId) {
    //     try {
    //     List<LecturerDTO> lecturers = lecturerService.getLecturersByDepartmentId(departmentId);
        
    //     if (!lecturers.isEmpty()) {
    //                     responseDTO.setCode(VarList.RSP_SUCCESS);
    //                     responseDTO.setMessage("Lecturers found.");
    //                     responseDTO.setContent(lecturers);
    //                     return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    //                 } 
    //                 else {
    //                     responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
    //                     responseDTO.setMessage("No lecturers found in this department.");
    //                     return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    //                             }
    //                         } catch (Exception ex) {
    //                             responseDTO.setCode(VarList.RSP_ERROR);
    //                             responseDTO.setMessage("Error: " + ex.getMessage());
    //                             return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    //                         }

        
    // }



}

