package com.demo.StudentManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.StudentManagement.dto.StudentDTO;
import com.demo.StudentManagement.model.Student;
import com.demo.StudentManagement.repository.StudentRepository;
import com.demo.StudentManagement.util.VarList;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public String saveStudent(StudentDTO studentDTO) {
        if (studentDTO.getId() != null && studentRepository.existsById(studentDTO.getId())) {
            return VarList.RSP_DUPLICATED; 
        }
 
        Student student = modelMapper.map(studentDTO, Student.class);
        student.setId(null);  

        studentRepository.save(student); 
        return VarList.RSP_SUCCESS; 
    }


    public String updateStudent(StudentDTO studentDTO) {
        Optional<Student> existingStudent = studentRepository.findById(studentDTO.getId());

        if (existingStudent.isPresent()) {
            Student updatedStudent = modelMapper.map(studentDTO, Student.class);
            studentRepository.save(updatedStudent);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND; 
        }
    }


    public List<StudentDTO> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }


    public StudentDTO getStudentById(int id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(value -> modelMapper.map(value, StudentDTO.class)).orElse(null);
    }
    

    public StudentDTO getStudentByEmail(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);
        return student.map(value -> modelMapper.map(value, StudentDTO.class)).orElse(null);
    }

  
    public List<StudentDTO> getStudentsByName(String name) {
        List<Student> students = studentRepository.findByNameContainingIgnoreCase(name);
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }



    public String deleteStudent(int id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return VarList.RSP_SUCCESS; 
        } else {
            return VarList.RSP_NO_DATA_FOUND; 
        }
    }

}
