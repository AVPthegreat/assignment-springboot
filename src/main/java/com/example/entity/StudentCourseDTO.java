package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to hold the result of the INNER JOIN query
 * between Students and Courses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseDTO {
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private String department;
    private Long courseId;
    private String courseName;
    private String courseCode;
    private String instructor;
    private int credits;
}
