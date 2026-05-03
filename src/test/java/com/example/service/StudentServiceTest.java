package com.example.service;

import com.example.entity.Student;
import com.example.entity.StudentCourseDTO;
import com.example.exception.DuplicateEmailException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StudentService Unit Tests (Mockito)")
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student sampleStudent;

    @BeforeEach
    void setUp() {
        sampleStudent = Student.builder()
            .id(1L)
            .name("Arjun Krishnamurthy")
            .email("arjun@test.edu")
            .phone("9876543210")
            .department("Computer Science")
            .year(2)
            .build();
    }

    // ===== GET ALL STUDENTS =====
    @Test
    @DisplayName("getAllStudents() should return all students from repository")
    void testGetAllStudents() {
        List<Student> mockList = Arrays.asList(sampleStudent,
            Student.builder().id(2L).name("Priya").email("p@t.edu")
                .phone("9000000000").department("CS").year(1).build());
        when(studentRepository.findAll()).thenReturn(mockList);

        List<Student> result = studentService.getAllStudents();

        assertThat(result).hasSize(2);
        verify(studentRepository, times(1)).findAll();
    }

    // ===== GET BY ID =====
    @Test
    @DisplayName("getStudentById() should return student when found")
    void testGetStudentByIdFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        Optional<Student> result = studentService.getStudentById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Arjun Krishnamurthy");
    }

    @Test
    @DisplayName("getStudentById() should return empty when not found")
    void testGetStudentByIdNotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.getStudentById(99L);

        assertThat(result).isEmpty();
    }

    // ===== SAVE STUDENT =====
    @Test
    @DisplayName("saveStudent() should save and return student when email is unique")
    void testSaveStudentSuccess() {
        when(studentRepository.findByEmail("arjun@test.edu")).thenReturn(Optional.empty());
        when(studentRepository.save(sampleStudent)).thenReturn(sampleStudent);

        Student saved = studentService.saveStudent(sampleStudent);

        assertThat(saved.getName()).isEqualTo("Arjun Krishnamurthy");
        verify(studentRepository).save(sampleStudent);
    }

    @Test
    @DisplayName("saveStudent() should throw DuplicateEmailException when email exists")
    void testSaveStudentDuplicateEmail() {
        when(studentRepository.findByEmail("arjun@test.edu"))
            .thenReturn(Optional.of(sampleStudent));

        assertThatThrownBy(() -> studentService.saveStudent(sampleStudent))
            .isInstanceOf(DuplicateEmailException.class)
            .hasMessageContaining("arjun@test.edu");

        verify(studentRepository, never()).save(any());
    }

    // ===== UPDATE STUDENT =====
    @Test
    @DisplayName("updateStudent() should update and return modified student")
    void testUpdateStudentSuccess() {
        Student updatedData = Student.builder()
            .name("Arjun K. Updated")
            .email("arjun@test.edu")
            .phone("9111111111")
            .department("Electronics")
            .year(3)
            .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));
        when(studentRepository.save(any(Student.class))).thenAnswer(inv -> inv.getArgument(0));

        Student result = studentService.updateStudent(1L, updatedData);

        assertThat(result.getName()).isEqualTo("Arjun K. Updated");
        assertThat(result.getDepartment()).isEqualTo("Electronics");
        assertThat(result.getYear()).isEqualTo(3);
    }

    @Test
    @DisplayName("updateStudent() should throw ResourceNotFoundException when student missing")
    void testUpdateStudentNotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            studentService.updateStudent(99L, sampleStudent))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    @DisplayName("updateStudent() should throw DuplicateEmailException when new email taken")
    void testUpdateStudentDuplicateEmail() {
        Student existing = Student.builder()
            .id(1L).name("Old").email("old@test.edu")
            .phone("9000000000").department("CS").year(1).build();

        Student updated = Student.builder()
            .name("Updated").email("arjun@test.edu")
            .phone("9000000001").department("CS").year(2).build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(studentRepository.findByEmail("arjun@test.edu"))
            .thenReturn(Optional.of(sampleStudent));

        assertThatThrownBy(() -> studentService.updateStudent(1L, updated))
            .isInstanceOf(DuplicateEmailException.class);
    }

    // ===== DELETE =====
    @Test
    @DisplayName("deleteStudent() should delete when student exists")
    void testDeleteStudentSuccess() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));
        doNothing().when(studentRepository).delete(sampleStudent);

        studentService.deleteStudent(1L);

        verify(studentRepository).delete(sampleStudent);
    }

    @Test
    @DisplayName("deleteStudent() should throw ResourceNotFoundException when not found")
    void testDeleteStudentNotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.deleteStudent(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    // ===== INNER JOIN QUERY =====
    @Test
    @DisplayName("getStudentsWithCourses() should call repository INNER JOIN method")
    void testGetStudentsWithCourses() {
        List<StudentCourseDTO> mockDTOs = List.of(
            new StudentCourseDTO(1L, "Arjun", "arjun@test.edu", "CS",
                1L, "Data Structures", "CS201", "Dr. Kumar", 4)
        );
        when(studentRepository.findAllStudentsWithCourses()).thenReturn(mockDTOs);

        List<StudentCourseDTO> result = studentService.getStudentsWithCourses();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStudentName()).isEqualTo("Arjun");
        assertThat(result.get(0).getCourseCode()).isEqualTo("CS201");
        verify(studentRepository).findAllStudentsWithCourses();
    }

    // ===== EMAIL EXISTS =====
    @Test
    @DisplayName("emailExists() should return true when email found")
    void testEmailExists() {
        when(studentRepository.findByEmail("arjun@test.edu"))
            .thenReturn(Optional.of(sampleStudent));

        assertThat(studentService.emailExists("arjun@test.edu")).isTrue();
    }

    @Test
    @DisplayName("emailExists() should return false when email not found")
    void testEmailNotExists() {
        when(studentRepository.findByEmail("nope@test.edu")).thenReturn(Optional.empty());

        assertThat(studentService.emailExists("nope@test.edu")).isFalse();
    }
}
