package com.demo.StudentManagement.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.StudentManagement.model.Department;
import com.demo.StudentManagement.model.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {

    List<Lecturer> findByDepartment(Department department);

}
