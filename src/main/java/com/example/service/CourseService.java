package com.example.service;

import com.example.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAllCourses();

    Optional<Course> getCourseById(Long id);

    Course saveCourse(Course course);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);

    List<Course> getCoursesWithEnrolledStudents();

    List<Course> getCoursesByDepartment(String department);

    boolean courseCodeExists(String courseCode);
}
