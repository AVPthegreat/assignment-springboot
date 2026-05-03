package com.example.service;

import com.example.entity.Course;
import com.example.exception.DuplicateCourseCodeException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        log.debug("Fetching all courses");
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getCourseById(Long id) {
        log.debug("Fetching course with id: {}", id);
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) {
        log.debug("Saving new course: {}", course.getCourseName());
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new DuplicateCourseCodeException(
                "A course with code '" + course.getCourseCode() + "' already exists."
            );
        }
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public Course updateCourse(Long id, Course updatedCourse) {
        log.debug("Updating course with id: {}", id);
        Course existing = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        // Allow same course code for the same course
        if (!existing.getCourseCode().equals(updatedCourse.getCourseCode())) {
            if (courseRepository.existsByCourseCode(updatedCourse.getCourseCode())) {
                throw new DuplicateCourseCodeException(
                    "A course with code '" + updatedCourse.getCourseCode() + "' already exists."
                );
            }
        }

        existing.setCourseName(updatedCourse.getCourseName());
        existing.setCourseCode(updatedCourse.getCourseCode());
        existing.setInstructor(updatedCourse.getInstructor());
        existing.setCredits(updatedCourse.getCredits());
        existing.setDepartment(updatedCourse.getDepartment());

        return courseRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        log.debug("Deleting course with id: {}", id);
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        courseRepository.delete(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCoursesWithEnrolledStudents() {
        log.debug("Fetching courses that have enrolled students (INNER JOIN)");
        return courseRepository.findCoursesWithEnrolledStudents();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCoursesByDepartment(String department) {
        return courseRepository.findByDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean courseCodeExists(String courseCode) {
        return courseRepository.existsByCourseCode(courseCode);
    }
}
