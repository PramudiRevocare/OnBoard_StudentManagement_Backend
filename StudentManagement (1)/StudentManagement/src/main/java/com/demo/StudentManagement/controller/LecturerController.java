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
import com.demo.StudentManagement.dto.LecturerDTO;
import com.demo.StudentManagement.dto.ResponseDTO;
import com.demo.StudentManagement.service.LecturerService;
import com.demo.StudentManagement.util.VarList;
import java.util.List;


@RestController
@RequestMapping("api/v1/lecturers")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private ResponseDTO responseDTO;



    //build POST rest api for Lecturer
    @PostMapping(value = "/saveLecturer")
    public ResponseEntity saveLecturer(@RequestBody LecturerDTO lecturerDTO){
        try{
            String res = lecturerService.saveLecturer(lecturerDTO);
            if (res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("success");
                responseDTO.setContent(lecturerDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } 
            else if (res.equals("04")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Lecturer already registered.");
                responseDTO.setContent(lecturerDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
                
            }
            else{
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



     // build PUT rest api for a Lecturer
    @PutMapping(value = "/updateLecturer")
    public ResponseEntity<ResponseDTO> updateLecturer(@RequestBody LecturerDTO lecturerDTO) {
        try {
            String res = lecturerService.updateLecturer(lecturerDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Lecturer updated successfully.");
                responseDTO.setContent(lecturerDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } 
            else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Lecturer not found.");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            } 
            else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Failed to update lecturer.");
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



    // GET: Get All Lecturers
    @GetMapping("/getAllLecturers")
    public ResponseEntity<ResponseDTO> getAllLecturers() {
        try {
            List<LecturerDTO> lecturers = lecturerService.getAllLecturers();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Lecturers retrieved successfully.");
            responseDTO.setContent(lecturers);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // GET: Get lecturers by ID
    @GetMapping("/getLecturer/{id}")
    public ResponseEntity<ResponseDTO> getLecturerById(@PathVariable int id) {
        try {
            LecturerDTO lecturerDTO = lecturerService.getLecturerById(id);
            if (lecturerDTO != null) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Lecturer found.");
                responseDTO.setContent(lecturerDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } 
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Lecturer not found.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






    // DELETE: Delete Student by ID
    @DeleteMapping("/deleteLecturer/{id}")
    public ResponseEntity<ResponseDTO> deleteLecturer(@PathVariable int id) {
        try {
            String res = lecturerService.deleteLecturer(id);
            if (res.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Lecturer deleted successfully.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } 
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Lecturer not found.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Get Lecturers by Department
    @GetMapping("/department/{department}")
    public ResponseEntity<ResponseDTO> getLecturersByDepartment(@PathVariable String department) {
        try {
            List<LecturerDTO> lecturers = lecturerService.getLecturersByDepartment(department);
            if (!lecturers.isEmpty()) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Lecturers found.");
                responseDTO.setContent(lecturers);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } 
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No lecturers found in this department.");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage("Error: " + ex.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

