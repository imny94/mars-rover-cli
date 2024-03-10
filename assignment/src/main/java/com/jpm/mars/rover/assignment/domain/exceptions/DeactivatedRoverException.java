package com.jpm.mars.rover.assignment.domain.exceptions;

public class DeactivatedRoverException extends Exception {
    public DeactivatedRoverException(String msg) {
        super(msg);
    }
}
