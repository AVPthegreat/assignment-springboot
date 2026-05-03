package com.example.repository;

import com.example.entity.Student;
import com.example.entity.StudentCourseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find a student by their email address.
     */
    Optional<Student> findByEmail(String email);

    /**
     * Find all students belonging to a specific department.
     */
    List<Student> findByDepartment(String department);

    /**
     * Find students by their academic year.
     */
    List<Student> findByYear(int year);

    /**
     * Custom INNER JOIN query to fetch all students with their enrolled courses.
     * Returns StudentCourseDTO objects combining data from both tables.
     */
    @Query("""
            SELECT new com.example.entity.StudentCourseDTO(
                s.id, s.name, s.email, s.department,
                c.id, c.courseName, c.courseCode, c.instructor, c.credits
            )
            FROM Student s
            INNER JOIN s.courses c
            ORDER BY s.name, c.courseName
           """)
    List<StudentCourseDTO> findAllStudentsWithCourses();

    /**
     * Search students by name (case-insensitive).
     */
    @Query("SELECT s FROM Student s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> searchByName(String name);

    /**
     * Count distinct students enrolled in a particular course code.
     */
    @Query("""
            SELECT COUNT(DISTINCT s)
            FROM Student s
            INNER JOIN s.courses c
            WHERE c.courseCode = :courseCode
           """)
    long countStudentsInCourse(String courseCode);
}
