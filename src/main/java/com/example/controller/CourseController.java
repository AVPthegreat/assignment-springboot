package com.example.controller;

import com.example.entity.Course;
import com.example.exception.DuplicateCourseCodeException;
import com.example.exception.ResourceNotFoundException;
import com.example.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    /**
     * READ - List all courses
     */
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("pageTitle", "All Courses");
        return "courses/list";
    }

    /**
     * READ - Courses with enrolled students (INNER JOIN)
     */
    @GetMapping("/active")
    public String listActiveCourses(Model model) {
        model.addAttribute("courses", courseService.getCoursesWithEnrolledStudents());
        model.addAttribute("pageTitle", "Active Courses (with Enrollments)");
        return "courses/active";
    }

    /**
     * READ - Single course detail
     */
    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        model.addAttribute("course", course);
        model.addAttribute("pageTitle", "Course Details");
        return "courses/detail";
    }

    /**
     * CREATE - Show add course form
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("pageTitle", "Add New Course");
        return "courses/add";
    }

    /**
     * CREATE - Handle form submission
     */
    @PostMapping("/add")
    public String addCourse(
            @Valid @ModelAttribute("course") Course course,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add New Course");
            return "courses/add";
        }

        try {
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("successMessage",
                "Course '" + course.getCourseName() + "' added successfully!");
            return "redirect:/courses";
        } catch (DuplicateCourseCodeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Add New Course");
            return "courses/add";
        }
    }

    /**
     * UPDATE - Show edit course form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        model.addAttribute("course", course);
        model.addAttribute("pageTitle", "Edit Course");
        return "courses/edit";
    }

    /**
     * UPDATE - Handle update form submission
     */
    @PostMapping("/edit/{id}")
    public String updateCourse(
            @PathVariable Long id,
            @Valid @ModelAttribute("course") Course course,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Course");
            return "courses/edit";
        }

        try {
            courseService.updateCourse(id, course);
            redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully!");
            return "redirect:/courses";
        } catch (DuplicateCourseCodeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pageTitle", "Edit Course");
            return "courses/edit";
        }
    }

    /**
     * DELETE - Remove a course
     */
    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/courses";
    }
}
