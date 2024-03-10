package com.jpm.mars.rover.assignment.domain.util;

import java.util.Optional;

// Custom class to represent the result along with error information
public class Result<T> {
    private final T value;
    private final Exception exception;

    private Result(T value, Exception exception) {
        this.value = value;
        this.exception = exception;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, null);
    }

    public static <T> Result<T> failure(Exception exception) {
        return new Result<>(null, exception);
    }

    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    public Optional<Exception> getException() {
        return Optional.ofNullable(exception);
    }

    public boolean isSuccess() {
        return exception == null;
    }
}
