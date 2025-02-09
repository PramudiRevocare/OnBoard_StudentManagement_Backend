package com.demo.StudentManagement.repositpry;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.StudentManagement.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByEmail(String email);
    
    List<Student> findByNameContainingIgnoreCase(String name);

}
