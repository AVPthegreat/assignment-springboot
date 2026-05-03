package com.example.controller;

import com.example.entity.Student;
import com.example.exception.DuplicateEmailException;
import com.example.exception.ResourceNotFoundException;
import com.example.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    /**
     * READ - List all students
     */
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("pageTitle", "All Students");
        return "students/list";
    }

    /**
     * READ - Show students with their courses (INNER JOIN result)
     */
    @GetMapping("/enrollments")
    public String listEnrollments(Model model) {
        model.addAttribute("enrollments", studentService.getStudentsWithCourses());
        model.addAttribute("pageTitle", "Student-Course Enrollments");
        return "students/enrollments";
    }

    /**
     * READ - Show single student detail
     */
    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Student Details");
        return "students/detail";
    }

    /**
     * CREATE - Show add student form
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Add New Student");
        return "students/add";
    }

    /**
     * CREATE - Handle form submission
     */
    @PostMapping("/add")
    public String addStudent(
            @Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add New Student");
            return "students/add";
        }

        try {
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("successMessage",
                "Student '" + student.getName() + "' added successfully!");
            return "redirect:/students";
        } catch (DuplicateEmailException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Add New Student");
            return "students/add";
        }
    }

    /**
     * UPDATE - Show edit student form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Edit Student");
        return "students/edit";
    }

    /**
     * UPDATE - Handle update form submission
     */
    @PostMapping("/edit/{id}")
    public String updateStudent(
            @PathVariable Long id,
            @Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Student");
            return "students/edit";
        }

        try {
            studentService.updateStudent(id, student);
            redirectAttributes.addFlashAttribute("successMessage",
                "Student updated successfully!");
            return "redirect:/students";
        } catch (DuplicateEmailException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Edit Student");
            return "students/edit";
        }
    }

    /**
     * DELETE - Remove a student
     */
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/students";
    }
}
