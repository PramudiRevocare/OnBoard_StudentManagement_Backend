package com.demo.StudentManagement.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.StudentManagement.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByNameContainingIgnoreCase(String name);

}
