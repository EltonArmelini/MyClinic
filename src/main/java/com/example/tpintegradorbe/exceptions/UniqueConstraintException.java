package com.example.tpintegradorbe.exceptions;

public class UniqueConstraintException extends RuntimeException{
    public UniqueConstraintException(String message) {
        super(message);
    }
}
