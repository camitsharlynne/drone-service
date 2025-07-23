package com.java.droneservice.exceptions;

public class DroneServiceException extends RuntimeException {

    public DroneServiceException(String message) {
        super(message);
    }

    public DroneServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
