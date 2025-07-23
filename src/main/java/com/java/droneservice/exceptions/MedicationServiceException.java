package com.java.droneservice.exceptions;

public class MedicationServiceException extends RuntimeException {

    public MedicationServiceException(String message) {
        super(message);
    }

    public MedicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
