package com.demo.StudentManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.StudentManagement.dto.LecturerDTO;
import com.demo.StudentManagement.model.Lecturer;
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
    private ModelMapper modelMapper;

    public String saveLecturer(LecturerDTO lecturerDTO) {
        if (lecturerDTO.getId() != null && lecturerRepository.existsById(lecturerDTO.getId())) {
            return VarList.RSP_DUPLICATED; 
        }
 
        Lecturer lecturer = modelMapper.map(lecturerDTO, Lecturer.class);
        lecturer.setId(null);  

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


    public List<LecturerDTO> getLecturersByDepartment(String department) {
        List<Lecturer> lecturers = lecturerRepository.findByDepartmentIgnoreCase(department);
        return lecturers.stream()
                .map(lecturer -> modelMapper.map(lecturer, LecturerDTO.class))
                .collect(Collectors.toList());
    }

}

