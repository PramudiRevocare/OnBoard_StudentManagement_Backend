package com.demo.StudentManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


    public String createLecturer(LecturerDTO lecturerDTO) {
        Optional<Department> department = departmentRepository.findById(lecturerDTO.getDepartmentId());

        if (department.isEmpty()) {
            return VarList.RSP_NO_DATA_FOUND;
        }

        List<Course> courses = courseRepository.findAllById(lecturerDTO.getCourseIds());

        Lecturer lecturer = modelMapper.map(lecturerDTO, Lecturer.class);
        lecturer.setDepartment(department.get());
        lecturer.setCourses(courses);
        lecturerRepository.save(lecturer);

        return VarList.RSP_SUCCESS;
    }


    public String updateLecturer(LecturerDTO lecturerDTO) {
        Optional<Lecturer> existingLecturer = lecturerRepository.findById(lecturerDTO.getId());

        if (existingLecturer.isPresent()) {
            Lecturer updatedLecturer = modelMapper.map(lecturerDTO, Lecturer.class);
            lecturerRepository.save(updatedLecturer);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND; 
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
        return lecturer.map(value -> modelMapper.map(value, LecturerDTO.class)).orElse(null);
    }



    public String deleteLecturer(int id) {
        if (lecturerRepository.existsById(id)) {
            lecturerRepository.deleteById(id);
            return VarList.RSP_SUCCESS; 
        } else {
            return VarList.RSP_NO_DATA_FOUND; 
        }
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

