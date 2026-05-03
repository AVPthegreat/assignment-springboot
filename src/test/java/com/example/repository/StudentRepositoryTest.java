package com.example.repository;

import com.example.entity.Course;
import com.example.entity.Student;
import com.example.entity.StudentCourseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("StudentRepository Integration Tests")
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Student student1;
    private Student student2;
    private Course course1;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        courseRepository.deleteAll();

        course1 = Course.builder()
            .courseName("Data Structures")
            .courseCode("CS201")
            .instructor("Dr. Kumar")
            .credits(4)
            .department("Computer Science")
            .build();
        course1 = courseRepository.save(course1);

        student1 = Student.builder()
            .name("Arjun Krishnamurthy")
            .email("arjun@test.edu")
            .phone("9876543210")
            .department("Computer Science")
            .year(2)
            .build();
        student1.enrollInCourse(course1);
        student1 = studentRepository.save(student1);

        student2 = Student.builder()
            .name("Priya Venkatesh")
            .email("priya@test.edu")
            .phone("9876543211")
            .department("Electronics")
            .year(3)
            .build();
        student2 = studentRepository.save(student2);
    }

    @Test
    @DisplayName("Should save and retrieve a student")
    void testSaveAndFindById() {
        Optional<Student> found = studentRepository.findById(student1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Arjun Krishnamurthy");
        assertThat(found.get().getEmail()).isEqualTo("arjun@test.edu");
    }

    @Test
    @DisplayName("Should find student by email")
    void testFindByEmail() {
        Optional<Student> found = studentRepository.findByEmail("arjun@test.edu");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Arjun Krishnamurthy");
    }

    @Test
    @DisplayName("Should return empty when email not found")
    void testFindByEmailNotFound() {
        Optional<Student> found = studentRepository.findByEmail("nonexistent@test.edu");
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should find students by department")
    void testFindByDepartment() {
        List<Student> csStudents = studentRepository.findByDepartment("Computer Science");
        assertThat(csStudents).hasSize(1);
        assertThat(csStudents.get(0).getName()).isEqualTo("Arjun Krishnamurthy");
    }

    @Test
    @DisplayName("Should find students by academic year")
    void testFindByYear() {
        List<Student> year2Students = studentRepository.findByYear(2);
        assertThat(year2Students).hasSize(1);
        assertThat(year2Students.get(0).getName()).isEqualTo("Arjun Krishnamurthy");
    }

    @Test
    @DisplayName("Should return all students")
    void testFindAll() {
        List<Student> all = studentRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    @DisplayName("Should execute INNER JOIN and return enrolled students with courses")
    void testFindAllStudentsWithCourses() {
        List<StudentCourseDTO> results = studentRepository.findAllStudentsWithCourses();
        // Only student1 is enrolled in a course; student2 has no enrollment
        assertThat(results).hasSize(1);
        StudentCourseDTO dto = results.get(0);
        assertThat(dto.getStudentName()).isEqualTo("Arjun Krishnamurthy");
        assertThat(dto.getCourseName()).isEqualTo("Data Structures");
        assertThat(dto.getCourseCode()).isEqualTo("CS201");
    }

    @Test
    @DisplayName("Should search students by name (case-insensitive)")
    void testSearchByName() {
        List<Student> results = studentRepository.searchByName("arjun");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getEmail()).isEqualTo("arjun@test.edu");
    }

    @Test
    @DisplayName("Should count students in a specific course")
    void testCountStudentsInCourse() {
        long count = studentRepository.countStudentsInCourse("CS201");
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should update a student record")
    void testUpdateStudent() {
        student1.setName("Arjun K. Updated");
        student1.setYear(3);
        Student updated = studentRepository.save(student1);

        assertThat(updated.getName()).isEqualTo("Arjun K. Updated");
        assertThat(updated.getYear()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should delete a student")
    void testDeleteStudent() {
        studentRepository.delete(student2);
        Optional<Student> found = studentRepository.findById(student2.getId());
        assertThat(found).isEmpty();
    }
}
