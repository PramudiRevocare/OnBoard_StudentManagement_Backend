package com.demo.StudentManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.StudentManagement.service.CourseService;
import com.demo.StudentManagement.dto.CourseDTO;
import com.demo.StudentManagement.dto.DepartmentDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


    //build POST rest api for Student
    @PostMapping(value = "/saveCourse")   
    public ResponseEntity<CourseDTO> saveCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO savedCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }



    // // build PUT rest api for a Course
    // @PutMapping(value = "/updateCourse")
    // public ResponseEntity<ResponseDTO> updateCourse(@RequestBody CourseDTO courseDTO) {
    //     try {
    //         String res = courseService.updateCourse(courseDTO);
    //         if (res.equals(VarList.RSP_SUCCESS)) {
    //             responseDTO.setCode(VarList.RSP_SUCCESS);
    //             responseDTO.setMessage("Course updated successfully.");
    //             responseDTO.setContent(courseDTO);
    //             return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    //         } 
    //         else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
    //             responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
    //             responseDTO.setMessage("Course not found.");
    //             responseDTO.setContent(null);
    //             return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    //         } else {
    //             responseDTO.setCode(VarList.RSP_FAIL);
    //             responseDTO.setMessage("Failed to update Course.");
    //             responseDTO.setContent(null);
    //             return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    //         }
    //     } catch (Exception ex) {
    //         responseDTO.setCode(VarList.RSP_ERROR);
    //         responseDTO.setMessage("Error: " + ex.getMessage());
    //         responseDTO.setContent(null);
    //         return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable int id, @RequestBody CourseDTO courseDTO) {
        try {
            CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    // GET: Get All Courses
    @GetMapping("/getAllCourses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        try{
            List<CourseDTO> courseDTO = courseService.getAllCourses();
            return ResponseEntity.ok(courseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    // GET: Get Course by ID
    @GetMapping("/getCourse/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable int id) {
        System.out.println("Received ID in Controller: " + id);
        try {
            CourseDTO courseDTO = courseService.getCourseById(id);
            return ResponseEntity.ok(courseDTO);
        } catch (RuntimeException e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    // // GET: Get Course by Name
    // @GetMapping("/getCoursesByName/{name}")
    // public ResponseEntity<ResponseDTO> getCoursesByName(@PathVariable String name) {
    //     try {
    //         List<CourseDTO> courses = courseService.getCoursesByName(name);
    //         if (!courses.isEmpty()) {
    //             responseDTO.setCode(VarList.RSP_SUCCESS);
    //             responseDTO.setMessage("Course found.");
    //             responseDTO.setContent(courses);
    //             return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    //         } else {
    //             responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
    //             responseDTO.setMessage("No course found with the given name.");
    //             return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    //         }
    //     } catch (Exception ex) {
    //         responseDTO.setCode(VarList.RSP_ERROR);
    //         responseDTO.setMessage("Error: " + ex.getMessage());
    //         return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }


  // DELETE: Delete Course by ID
    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok("Course deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
    }

    
    


}
