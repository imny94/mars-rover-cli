package com.jpm.mars.rover.assignment.domain.exceptions;

public class UnsupportedUserCommandException extends Exception {
    public UnsupportedUserCommandException(String msg) {
        super(msg);
    }
}
