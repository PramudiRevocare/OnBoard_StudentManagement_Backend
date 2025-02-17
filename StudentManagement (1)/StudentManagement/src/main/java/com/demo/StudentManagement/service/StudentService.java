package com.demo.StudentManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.StudentManagement.dto.DepartmentDTO;
import com.demo.StudentManagement.dto.LecturerDTO;
import com.demo.StudentManagement.dto.StudentDTO;
import com.demo.StudentManagement.model.Course;
import com.demo.StudentManagement.model.Department;
import com.demo.StudentManagement.model.Lecturer;
import com.demo.StudentManagement.model.Student;
import com.demo.StudentManagement.repository.CourseRepository;
import com.demo.StudentManagement.repository.DepartmentRepository;
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
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;


  
      public StudentDTO saveStudent(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentDTO.class);
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

        return studentList.stream().map(student -> {
            StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);

            if (student.getDepartment() != null) {
                studentDTO.setDepartmentName(student.getDepartment().getName());
            }

            if (student.getCourses() != null && !student.getCourses().isEmpty()) {
                studentDTO.setCourseNames(
                    student.getCourses().stream()
                            .map(course -> course.getName())
                            .collect(Collectors.toList())
                );
            }

            return studentDTO;
        }).collect(Collectors.toList());
    }


        public StudentDTO getStudentById(int id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(value -> modelMapper.map(value, StudentDTO.class))
        .orElseThrow(() -> new RuntimeException("Student not found"));
    }
    

    public StudentDTO getStudentByEmail(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);
        return student.map(value -> modelMapper.map(value, StudentDTO.class)).orElse(null);
    }

  
    public StudentDTO getStudentByName(String name) {
        Optional<Student> student = studentRepository.findByNameContainingIgnoreCase(name).stream().findFirst();
        return student.map(value -> modelMapper.map(value, StudentDTO.class))
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }


    public void deleteStudent(int id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
                studentRepository.delete(student);
    }

}
