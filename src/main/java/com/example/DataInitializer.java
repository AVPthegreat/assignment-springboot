package com.example;

import com.example.entity.Course;
import com.example.entity.Student;
import com.example.repository.CourseRepository;
import com.example.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Populates the database with 10 sample students and 10 sample courses,
 * and creates enrollment relationships between them.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Initializing sample data...");

        // --- Create 10 Courses ---
        List<Course> courses = Arrays.asList(
            Course.builder().courseName("Data Structures and Algorithms").courseCode("CS201")
                .instructor("Dr. Ramesh Kumar").credits(4).department("Computer Science").build(),
            Course.builder().courseName("Database Management Systems").courseCode("CS301")
                .instructor("Dr. Priya Nair").credits(4).department("Computer Science").build(),
            Course.builder().courseName("Operating Systems").courseCode("CS302")
                .instructor("Prof. Anil Sharma").credits(3).department("Computer Science").build(),
            Course.builder().courseName("Computer Networks").courseCode("CS401")
                .instructor("Dr. Sunita Rao").credits(3).department("Computer Science").build(),
            Course.builder().courseName("Machine Learning Fundamentals").courseCode("CS501")
                .instructor("Dr. Vikram Patel").credits(4).department("Computer Science").build(),
            Course.builder().courseName("Engineering Mathematics").courseCode("MA201")
                .instructor("Prof. Meena Iyer").credits(4).department("Mathematics").build(),
            Course.builder().courseName("Probability and Statistics").courseCode("MA301")
                .instructor("Dr. Suresh Pillai").credits(3).department("Mathematics").build(),
            Course.builder().courseName("Digital Electronics").courseCode("EC201")
                .instructor("Dr. Kavitha Menon").credits(3).department("Electronics").build(),
            Course.builder().courseName("Microprocessors and Microcontrollers").courseCode("EC301")
                .instructor("Prof. Rajesh Verma").credits(4).department("Electronics").build(),
            Course.builder().courseName("Software Engineering").courseCode("CS403")
                .instructor("Dr. Anita Desai").credits(3).department("Computer Science").build()
        );

        List<Course> savedCourses = courseRepository.saveAll(courses);
        log.info("Saved {} courses", savedCourses.size());

        // --- Create 10 Students ---
        List<Student> students = Arrays.asList(
            Student.builder().name("Arjun Krishnamurthy").email("arjun.k@university.edu")
                .phone("9876543210").department("Computer Science").year(2).build(),
            Student.builder().name("Priya Venkatesh").email("priya.v@university.edu")
                .phone("9876543211").department("Computer Science").year(3).build(),
            Student.builder().name("Rohit Sharma").email("rohit.s@university.edu")
                .phone("9876543212").department("Electronics").year(2).build(),
            Student.builder().name("Sneha Reddy").email("sneha.r@university.edu")
                .phone("9876543213").department("Computer Science").year(1).build(),
            Student.builder().name("Kiran Bhat").email("kiran.b@university.edu")
                .phone("9876543214").department("Mathematics").year(4).build(),
            Student.builder().name("Divya Menon").email("divya.m@university.edu")
                .phone("9876543215").department("Electronics").year(3).build(),
            Student.builder().name("Aakash Gupta").email("aakash.g@university.edu")
                .phone("9876543216").department("Computer Science").year(2).build(),
            Student.builder().name("Lakshmi Prasad").email("lakshmi.p@university.edu")
                .phone("9876543217").department("Computer Science").year(4).build(),
            Student.builder().name("Vikram Nair").email("vikram.n@university.edu")
                .phone("9876543218").department("Mathematics").year(1).build(),
            Student.builder().name("Ananya Singh").email("ananya.s@university.edu")
                .phone("9876543219").department("Computer Science").year(3).build()
        );

        List<Student> savedStudents = studentRepository.saveAll(students);
        log.info("Saved {} students", savedStudents.size());

        // --- Create Enrollments (many-to-many) ---
        // Arjun enrolled in CS201, CS301, MA201
        savedStudents.get(0).enrollInCourse(savedCourses.get(0));
        savedStudents.get(0).enrollInCourse(savedCourses.get(1));
        savedStudents.get(0).enrollInCourse(savedCourses.get(5));

        // Priya enrolled in CS301, CS302, CS501
        savedStudents.get(1).enrollInCourse(savedCourses.get(1));
        savedStudents.get(1).enrollInCourse(savedCourses.get(2));
        savedStudents.get(1).enrollInCourse(savedCourses.get(4));

        // Rohit enrolled in EC201, EC301, MA201
        savedStudents.get(2).enrollInCourse(savedCourses.get(7));
        savedStudents.get(2).enrollInCourse(savedCourses.get(8));
        savedStudents.get(2).enrollInCourse(savedCourses.get(5));

        // Sneha enrolled in CS201, MA201, MA301
        savedStudents.get(3).enrollInCourse(savedCourses.get(0));
        savedStudents.get(3).enrollInCourse(savedCourses.get(5));
        savedStudents.get(3).enrollInCourse(savedCourses.get(6));

        // Kiran enrolled in MA201, MA301, CS501
        savedStudents.get(4).enrollInCourse(savedCourses.get(5));
        savedStudents.get(4).enrollInCourse(savedCourses.get(6));
        savedStudents.get(4).enrollInCourse(savedCourses.get(4));

        // Divya enrolled in EC201, EC301, CS301
        savedStudents.get(5).enrollInCourse(savedCourses.get(7));
        savedStudents.get(5).enrollInCourse(savedCourses.get(8));
        savedStudents.get(5).enrollInCourse(savedCourses.get(1));

        // Aakash enrolled in CS201, CS401, CS403
        savedStudents.get(6).enrollInCourse(savedCourses.get(0));
        savedStudents.get(6).enrollInCourse(savedCourses.get(3));
        savedStudents.get(6).enrollInCourse(savedCourses.get(9));

        // Lakshmi enrolled in CS401, CS403, CS501
        savedStudents.get(7).enrollInCourse(savedCourses.get(3));
        savedStudents.get(7).enrollInCourse(savedCourses.get(9));
        savedStudents.get(7).enrollInCourse(savedCourses.get(4));

        // Vikram enrolled in MA201, MA301, EC201
        savedStudents.get(8).enrollInCourse(savedCourses.get(5));
        savedStudents.get(8).enrollInCourse(savedCourses.get(6));
        savedStudents.get(8).enrollInCourse(savedCourses.get(7));

        // Ananya enrolled in CS302, CS401, CS403, CS501
        savedStudents.get(9).enrollInCourse(savedCourses.get(2));
        savedStudents.get(9).enrollInCourse(savedCourses.get(3));
        savedStudents.get(9).enrollInCourse(savedCourses.get(9));
        savedStudents.get(9).enrollInCourse(savedCourses.get(4));

        studentRepository.saveAll(savedStudents);
        log.info("Enrollment data populated successfully!");
        log.info("Database initialization complete.");
    }
}
