package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("pageTitle", "Home");
        return "home";
    }

    /**
     * Global exception handler for ResourceNotFoundException.
     */
    @ControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public String handleNotFound(ResourceNotFoundException ex, Model model) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("pageTitle", "Not Found");
            return "error/404";
        }

        @ExceptionHandler(Exception.class)
        public String handleGenericError(Exception ex, Model model) {
            model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
            model.addAttribute("pageTitle", "Error");
            return "error/500";
        }
    }
}
