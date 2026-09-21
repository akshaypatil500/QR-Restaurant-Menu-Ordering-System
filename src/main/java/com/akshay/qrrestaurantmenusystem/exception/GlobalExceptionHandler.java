package com.akshay.qrrestaurantmenusystem.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ==========================================
    // Handle Runtime Exceptions
    // ==========================================

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex,
                                         Model model) {

        model.addAttribute("errorMessage", ex.getMessage());

        return "error/error-page";
    }

    // ==========================================
    // Handle All Other Exceptions
    // ==========================================

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex,
                                  Model model) {

        model.addAttribute(
                "errorMessage",
                "Something went wrong. Please try again.");

        return "error/error-page";
    }

}