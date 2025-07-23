package com.java.droneservice.exceptions;

import javax.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(DroneServiceException.class)
    public ResponseEntity<String> handleDroneRegistration(DroneServiceException ex) {
        String detailedMessage = ex.getMessage();
        if (ex.getCause() != null) {
            detailedMessage += " | Root cause: " + ex.getCause().getMessage();
        }

        return ResponseEntity.badRequest().body(detailedMessage);
    }

    @ExceptionHandler(MedicationServiceException.class)
    public ResponseEntity<String> handleMedicationService(MedicationServiceException ex) {
        String detailedMessage = ex.getMessage();
        if (ex.getCause() != null) {
            detailedMessage += " | Root cause: " + ex.getCause().getMessage();
        }

        return ResponseEntity.badRequest().body(detailedMessage);
    }
}
