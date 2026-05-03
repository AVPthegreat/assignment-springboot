package com.example.service;

import com.example.entity.Course;
import com.example.exception.DuplicateCourseCodeException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.CourseRepository;
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
@DisplayName("CourseService Unit Tests (Mockito)")
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course sampleCourse;

    @BeforeEach
    void setUp() {
        sampleCourse = Course.builder()
            .id(1L)
            .courseName("Data Structures and Algorithms")
            .courseCode("CS201")
            .instructor("Dr. Ramesh Kumar")
            .credits(4)
            .department("Computer Science")
            .build();
    }

    // ===== GET ALL =====
    @Test
    @DisplayName("getAllCourses() should return all courses")
    void testGetAllCourses() {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(sampleCourse));

        List<Course> result = courseService.getAllCourses();

        assertThat(result).hasSize(1);
        verify(courseRepository, times(1)).findAll();
    }

    // ===== GET BY ID =====
    @Test
    @DisplayName("getCourseById() should return course when found")
    void testGetCourseByIdFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(sampleCourse));

        Optional<Course> result = courseService.getCourseById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getCourseCode()).isEqualTo("CS201");
    }

    @Test
    @DisplayName("getCourseById() should return empty when not found")
    void testGetCourseByIdNotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Course> result = courseService.getCourseById(99L);

        assertThat(result).isEmpty();
    }

    // ===== SAVE COURSE =====
    @Test
    @DisplayName("saveCourse() should save when course code is unique")
    void testSaveCourseSuccess() {
        when(courseRepository.existsByCourseCode("CS201")).thenReturn(false);
        when(courseRepository.save(sampleCourse)).thenReturn(sampleCourse);

        Course saved = courseService.saveCourse(sampleCourse);

        assertThat(saved.getCourseCode()).isEqualTo("CS201");
        verify(courseRepository).save(sampleCourse);
    }

    @Test
    @DisplayName("saveCourse() should throw DuplicateCourseCodeException when code exists")
    void testSaveCourseDuplicateCode() {
        when(courseRepository.existsByCourseCode("CS201")).thenReturn(true);

        assertThatThrownBy(() -> courseService.saveCourse(sampleCourse))
            .isInstanceOf(DuplicateCourseCodeException.class)
            .hasMessageContaining("CS201");

        verify(courseRepository, never()).save(any());
    }

    // ===== UPDATE COURSE =====
    @Test
    @DisplayName("updateCourse() should update and return modified course")
    void testUpdateCourseSuccess() {
        Course updatedData = Course.builder()
            .courseName("Advanced Algorithms")
            .courseCode("CS201")
            .instructor("Prof. New")
            .credits(3)
            .department("Computer Science")
            .build();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(sampleCourse));
        when(courseRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Course result = courseService.updateCourse(1L, updatedData);

        assertThat(result.getCourseName()).isEqualTo("Advanced Algorithms");
        assertThat(result.getInstructor()).isEqualTo("Prof. New");
        assertThat(result.getCredits()).isEqualTo(3);
    }

    @Test
    @DisplayName("updateCourse() should throw ResourceNotFoundException when not found")
    void testUpdateCourseNotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.updateCourse(99L, sampleCourse))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    @DisplayName("updateCourse() should throw DuplicateCourseCodeException on code conflict")
    void testUpdateCourseDuplicateCode() {
        Course existing = Course.builder()
            .id(1L).courseName("Old").courseCode("CS100")
            .instructor("Old Prof").credits(3).department("CS").build();

        Course updatedData = Course.builder()
            .courseName("New").courseCode("CS201")
            .instructor("New Prof").credits(4).department("CS").build();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(courseRepository.existsByCourseCode("CS201")).thenReturn(true);

        assertThatThrownBy(() -> courseService.updateCourse(1L, updatedData))
            .isInstanceOf(DuplicateCourseCodeException.class);
    }

    // ===== DELETE COURSE =====
    @Test
    @DisplayName("deleteCourse() should delete when course exists")
    void testDeleteCourseSuccess() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(sampleCourse));
        doNothing().when(courseRepository).delete(sampleCourse);

        courseService.deleteCourse(1L);

        verify(courseRepository).delete(sampleCourse);
    }

    @Test
    @DisplayName("deleteCourse() should throw ResourceNotFoundException when not found")
    void testDeleteCourseNotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.deleteCourse(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    // ===== INNER JOIN / ACTIVE COURSES =====
    @Test
    @DisplayName("getCoursesWithEnrolledStudents() should return active courses only")
    void testGetCoursesWithEnrolledStudents() {
        when(courseRepository.findCoursesWithEnrolledStudents())
            .thenReturn(Arrays.asList(sampleCourse));

        List<Course> result = courseService.getCoursesWithEnrolledStudents();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCourseCode()).isEqualTo("CS201");
        verify(courseRepository).findCoursesWithEnrolledStudents();
    }

    // ===== CODE EXISTS =====
    @Test
    @DisplayName("courseCodeExists() should return true when code present")
    void testCourseCodeExists() {
        when(courseRepository.existsByCourseCode("CS201")).thenReturn(true);
        assertThat(courseService.courseCodeExists("CS201")).isTrue();
    }
}
