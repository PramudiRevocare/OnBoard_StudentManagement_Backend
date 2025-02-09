package com.demo.StudentManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LecturerDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String department;

}
