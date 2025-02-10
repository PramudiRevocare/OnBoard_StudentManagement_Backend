package com.demo.StudentManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.StudentManagement.dto.CourseDTO;
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



    public String createCourse(CourseDTO courseDTO) {
        Optional<Department> department = departmentRepository.findById(courseDTO.getDepartmentId());

        if (department.isEmpty()) {
            return VarList.RSP_NO_DATA_FOUND; 
        }

        Course course = modelMapper.map(courseDTO, Course.class);
        course.setDepartment(department.get());
        courseRepository.save(course);

        return VarList.RSP_SUCCESS; 
    }


    public String updateCourse(CourseDTO courseDTO) {
        Optional<Course> existingCourse = courseRepository.findById(courseDTO.getId());

        if (existingCourse.isPresent()) {
            Course updatedCourse = modelMapper.map(courseDTO, Course.class);
            courseRepository.save(updatedCourse);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND; 
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
        return course.map(value -> modelMapper.map(value, CourseDTO.class)).orElse(null);
    }

  
    public List<CourseDTO> getCoursesByName(String name) {
        List<Course> courses = courseRepository.findByNameContainingIgnoreCase(name);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }


    public String deleteCourse(int id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return VarList.RSP_SUCCESS; 
        } else {
            return VarList.RSP_NO_DATA_FOUND; 
        }
    }

}
