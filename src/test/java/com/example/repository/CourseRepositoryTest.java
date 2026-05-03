package com.example.repository;

import com.example.entity.Course;
import com.example.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("CourseRepository Integration Tests")
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        courseRepository.deleteAll();

        course1 = Course.builder()
            .courseName("Data Structures and Algorithms")
            .courseCode("CS201")
            .instructor("Dr. Ramesh Kumar")
            .credits(4)
            .department("Computer Science")
            .build();

        course2 = Course.builder()
            .courseName("Engineering Mathematics")
            .courseCode("MA201")
            .instructor("Prof. Meena Iyer")
            .credits(4)
            .department("Mathematics")
            .build();

        course1 = courseRepository.save(course1);
        course2 = courseRepository.save(course2);

        // Enroll a student in course1 only
        Student student = Student.builder()
            .name("Test Student")
            .email("test@test.edu")
            .phone("9000000000")
            .department("Computer Science")
            .year(1)
            .build();
        student.enrollInCourse(course1);
        studentRepository.save(student);
    }

    @Test
    @DisplayName("Should save and retrieve a course by ID")
    void testSaveAndFindById() {
        Optional<Course> found = courseRepository.findById(course1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getCourseName()).isEqualTo("Data Structures and Algorithms");
        assertThat(found.get().getCourseCode()).isEqualTo("CS201");
    }

    @Test
    @DisplayName("Should find course by course code")
    void testFindByCourseCode() {
        Optional<Course> found = courseRepository.findByCourseCode("CS201");
        assertThat(found).isPresent();
        assertThat(found.get().getInstructor()).isEqualTo("Dr. Ramesh Kumar");
    }

    @Test
    @DisplayName("Should return empty for non-existent course code")
    void testFindByCourseCodeNotFound() {
        Optional<Course> found = courseRepository.findByCourseCode("ZZZZ999");
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should find courses by department")
    void testFindByDepartment() {
        List<Course> maths = courseRepository.findByDepartment("Mathematics");
        assertThat(maths).hasSize(1);
        assertThat(maths.get(0).getCourseCode()).isEqualTo("MA201");
    }

    @Test
    @DisplayName("Should find courses by instructor")
    void testFindByInstructor() {
        List<Course> courses = courseRepository.findByInstructor("Dr. Ramesh Kumar");
        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getCourseCode()).isEqualTo("CS201");
    }

    @Test
    @DisplayName("Should check course code existence")
    void testExistsByCourseCode() {
        assertThat(courseRepository.existsByCourseCode("CS201")).isTrue();
        assertThat(courseRepository.existsByCourseCode("FAKE999")).isFalse();
    }

    @Test
    @DisplayName("Should return only courses with enrolled students (INNER JOIN)")
    void testFindCoursesWithEnrolledStudents() {
        List<Course> active = courseRepository.findCoursesWithEnrolledStudents();
        // Only course1 has an enrolled student
        assertThat(active).hasSize(1);
        assertThat(active.get(0).getCourseCode()).isEqualTo("CS201");
    }

    @Test
    @DisplayName("Should find courses with minimum credits")
    void testFindByCreditsGreaterThanEqual() {
        List<Course> highCredit = courseRepository.findByCreditsGreaterThanEqual(4);
        assertThat(highCredit).hasSize(2);
    }

    @Test
    @DisplayName("Should search courses by name (case-insensitive)")
    void testSearchByName() {
        List<Course> results = courseRepository.searchByName("mathematics");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getCourseCode()).isEqualTo("MA201");
    }

    @Test
    @DisplayName("Should update a course record")
    void testUpdateCourse() {
        course1.setInstructor("Dr. New Instructor");
        course1.setCredits(3);
        Course updated = courseRepository.save(course1);

        assertThat(updated.getInstructor()).isEqualTo("Dr. New Instructor");
        assertThat(updated.getCredits()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should delete a course")
    void testDeleteCourse() {
        courseRepository.delete(course2);
        Optional<Course> found = courseRepository.findById(course2.getId());
        assertThat(found).isEmpty();
    }
}
