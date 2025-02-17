package com.demo.StudentManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.StudentManagement.dto.CourseDTO;
import com.demo.StudentManagement.dto.DepartmentDTO;
import com.demo.StudentManagement.dto.LecturerDTO;
import com.demo.StudentManagement.model.Course;
import com.demo.StudentManagement.model.Department;
import com.demo.StudentManagement.model.Lecturer;
import com.demo.StudentManagement.repository.CourseRepository;
import com.demo.StudentManagement.repository.DepartmentRepository;
import com.demo.StudentManagement.repository.LecturerRepository;
import com.demo.StudentManagement.util.VarList;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

     @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;


    public LecturerDTO saveLecturer(LecturerDTO lecturerDTO) {
        Lecturer lecturer = modelMapper.map(lecturerDTO, Lecturer.class);
        Lecturer savedLecturer = lecturerRepository.save(lecturer);
        return modelMapper.map(savedLecturer, LecturerDTO.class);
    }

    
    public LecturerDTO updateLecturer(int id, LecturerDTO lecturerDTO) {
        System.out.println("Received ID in Controller: " + id);
        Optional<Lecturer> optionalLecturer = lecturerRepository.findById(id);
        if (optionalLecturer.isPresent()) {
            Lecturer lecturer = optionalLecturer.get();
            lecturer.setName(lecturerDTO.getName());
            lecturer.setPhone(lecturerDTO.getPhone());
            lecturer.setEmail(lecturerDTO.getEmail());

            if (lecturerDTO.getDepartmentId() != null) {
                Department department = departmentRepository.findById(lecturerDTO.getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Department not found"));
                lecturer.setDepartment(department);
            }

            if (lecturerDTO.getCourseIds() != null && !lecturerDTO.getCourseIds().isEmpty()) {
                List<Course> courses = courseRepository.findAllById(lecturerDTO.getCourseIds());
                if (courses.size() != lecturerDTO.getCourseIds().size()) {
                    throw new RuntimeException("One or more courses not found");
                }
                lecturer.setCourses(courses);
            }

            Lecturer updatedLecturer = lecturerRepository.save(lecturer);
            return modelMapper.map(updatedLecturer, LecturerDTO.class);
        } else {
            throw new RuntimeException("Lecturer not found");
        }
    }


    public List<LecturerDTO> getAllLecturers() {
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        return lecturerList.stream()
                .map(lecturer -> modelMapper.map(lecturer, LecturerDTO.class))
                .collect(Collectors.toList());
    }


    public LecturerDTO getLecturerById(int id) {
        Optional<Lecturer> lecturer = lecturerRepository.findById(id);
        return lecturer.map(value -> modelMapper.map(value, LecturerDTO.class))
        .orElseThrow(() -> new RuntimeException("lecturer not found"));
    }


    public void deleteLecturer(int id) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));
        
                lecturerRepository.delete(lecturer);
    }



    public List<LecturerDTO> getLecturersByDepartmentId(Integer departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        
        if (department.isEmpty()) {
            return null; 
        }

        List<Lecturer> lecturers = lecturerRepository.findByDepartment(department.get());

        return lecturers.stream()
                .map(lecturer -> modelMapper.map(lecturer, LecturerDTO.class))
                .collect(Collectors.toList());
    }

}

