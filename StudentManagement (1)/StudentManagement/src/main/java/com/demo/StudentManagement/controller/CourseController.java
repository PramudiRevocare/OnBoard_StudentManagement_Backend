package com.demo.StudentManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.StudentManagement.service.CourseService;
import com.demo.StudentManagement.util.VarList;
import com.demo.StudentManagement.dto.CourseDTO;
import com.demo.StudentManagement.dto.ResponseDTO;
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

     @Autowired
    private ResponseDTO responseDTO;


    //build POST rest api for Student
    @PostMapping(value = "/saveCourse")
    public ResponseEntity saveCourse(@RequestBody CourseDTO courseDTO){
        try{
            String res = courseService.saveCourse(courseDTO);
            if (res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("success");
                responseDTO.setContent(courseDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else if (res.equals("04")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Course already registered.");
                responseDTO.setContent(courseDTO);
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


    // build PUT rest api for a Course
    @PutMapping(value = "/updateCourse")
    public ResponseEntity<ResponseDTO> updateCourse(@RequestBody CourseDTO courseDTO) {
        try {
            String res = courseService.updateCourse(courseDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Course updated successfully.");
                responseDTO.setContent(courseDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } 
            else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Course not found.");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Failed to update Course.");
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



    // GET: Get All Courses
    @GetMapping("/getAllCourses")
    public ResponseEntity<ResponseDTO> getAllCourses() {
        try {
            List<CourseDTO> courses = courseService.getAllCourses();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Courses retrieved successfully.");
            responseDTO.setContent(courses);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // GET: Get Course by ID
    @GetMapping("/getCourse/{id}")
    public ResponseEntity<ResponseDTO> getCourseById(@PathVariable int id) {
        try {
            CourseDTO courseDTO = courseService.getCourseById(id);
            if (courseDTO != null) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage(" Course found.");
                responseDTO.setContent(courseDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage(" Course not found.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // GET: Get Course by Name
    @GetMapping("/getCoursesByName/{name}")
    public ResponseEntity<ResponseDTO> getCoursesByName(@PathVariable String name) {
        try {
            List<CourseDTO> courses = courseService.getCoursesByName(name);
            if (!courses.isEmpty()) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Course found.");
                responseDTO.setContent(courses);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No course found with the given name.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


  // DELETE: Delete Course by ID
    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<ResponseDTO> deleteCourse(@PathVariable int id) {
        try {
            String res = courseService.deleteCourse(id);
            if (res.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Course deleted successfully.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Course not found.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    
    


}
