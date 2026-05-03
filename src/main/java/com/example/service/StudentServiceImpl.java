package com.example.service;

import com.example.entity.Student;
import com.example.entity.StudentCourseDTO;
import com.example.exception.DuplicateEmailException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        log.debug("Fetching all students");
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(Long id) {
        log.debug("Fetching student with id: {}", id);
        return studentRepository.findById(id);
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        log.debug("Saving new student: {}", student.getName());
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new DuplicateEmailException(
                "A student with email '" + student.getEmail() + "' already exists."
            );
        }
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student updatedStudent) {
        log.debug("Updating student with id: {}", id);
        Student existing = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        // Check email uniqueness (allow same email for the same student)
        if (!existing.getEmail().equals(updatedStudent.getEmail())) {
            if (studentRepository.findByEmail(updatedStudent.getEmail()).isPresent()) {
                throw new DuplicateEmailException(
                    "A student with email '" + updatedStudent.getEmail() + "' already exists."
                );
            }
        }

        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setPhone(updatedStudent.getPhone());
        existing.setDepartment(updatedStudent.getDepartment());
        existing.setYear(updatedStudent.getYear());

        return studentRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        log.debug("Deleting student with id: {}", id);
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentCourseDTO> getStudentsWithCourses() {
        log.debug("Fetching students with courses (INNER JOIN)");
        return studentRepository.findAllStudentsWithCourses();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.searchByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }
}
