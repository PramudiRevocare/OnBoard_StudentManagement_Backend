package com.demo.StudentManagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer departmentId;
    private List<Integer> courseIds;

}
