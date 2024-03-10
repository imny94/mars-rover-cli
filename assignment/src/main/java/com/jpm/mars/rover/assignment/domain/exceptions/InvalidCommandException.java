package com.jpm.mars.rover.assignment.domain.exceptions;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(String msg) {
        super(msg);
    }
}
