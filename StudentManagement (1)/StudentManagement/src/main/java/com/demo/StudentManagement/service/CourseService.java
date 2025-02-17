package com.demo.StudentManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.StudentManagement.dto.CourseDTO;
import com.demo.StudentManagement.dto.DepartmentDTO;
import com.demo.StudentManagement.model.Course;
import com.demo.StudentManagement.model.Department;
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
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private ModelMapper modelMapper;


     public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        Course savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseDTO.class);
    }


    // public String updateCourse(CourseDTO courseDTO) {
    //     Optional<Course> existingCourse = courseRepository.findById(courseDTO.getId());

    //     if (existingCourse.isPresent()) {
    //         Course updatedCourse = modelMapper.map(courseDTO, Course.class);
    //         courseRepository.save(updatedCourse);
    //         return VarList.RSP_SUCCESS;
    //     } else {
    //         return VarList.RSP_NO_DATA_FOUND; 
    //     }
    // }
    public CourseDTO updateCourse(int id, CourseDTO courseDTO) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setName(courseDTO.getName());
            course.setDescription(courseDTO.getDescription());
            Course updatedCourse = courseRepository.save(course);
            return modelMapper.map(updatedCourse, CourseDTO.class);
        } else {
            throw new RuntimeException("Course not found");
        }
    }


    public List<CourseDTO> getAllCourses() {
        List<Course> courseList = courseRepository.findAll();
        return courseList.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }


    public CourseDTO getCourseById(int id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(value -> modelMapper.map(value, CourseDTO.class))
        .orElseThrow(() -> new RuntimeException("Course not found"));
    }

  
    public List<CourseDTO> getCoursesByName(String name) {
        List<Course> courses = courseRepository.findByNameContainingIgnoreCase(name);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }


    public void deleteCourse(int id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
                courseRepository.delete(course);
    }

}
