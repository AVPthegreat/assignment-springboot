package com.example.exception;

public class DuplicateCourseCodeException extends RuntimeException {
    public DuplicateCourseCodeException(String message) {
        super(message);
    }
}
