package com.example.tasktracker.exception;

/**
 * Исключение для случая, когда ресурс не найден.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}