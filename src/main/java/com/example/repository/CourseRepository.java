package com.example.repository;

import com.example.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Find a course by its unique course code.
     */
    Optional<Course> findByCourseCode(String courseCode);

    /**
     * Find all courses belonging to a specific department.
     */
    List<Course> findByDepartment(String department);

    /**
     * Find all courses offered by a specific instructor.
     */
    List<Course> findByInstructor(String instructor);

    /**
     * Find courses by minimum credit hours.
     */
    List<Course> findByCreditsGreaterThanEqual(int credits);

    /**
     * Custom query: get courses that have at least one enrolled student (INNER JOIN).
     */
    @Query("""
            SELECT DISTINCT c
            FROM Course c
            INNER JOIN c.students s
            ORDER BY c.courseName
           """)
    List<Course> findCoursesWithEnrolledStudents();

    /**
     * Custom query: search courses by name (case-insensitive).
     */
    @Query("SELECT c FROM Course c WHERE LOWER(c.courseName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Course> searchByName(String name);

    /**
     * Check if a course code already exists.
     */
    boolean existsByCourseCode(String courseCode);
}
