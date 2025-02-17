package com.demo.StudentManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.StudentManagement.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
