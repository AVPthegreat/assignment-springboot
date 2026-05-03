package com.example.service;

import com.example.entity.Student;
import com.example.entity.StudentCourseDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllStudents();

    Optional<Student> getStudentById(Long id);

    Student saveStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    List<Student> getStudentsByDepartment(String department);

    List<StudentCourseDTO> getStudentsWithCourses();

    List<Student> searchStudentsByName(String name);

    boolean emailExists(String email);
}
