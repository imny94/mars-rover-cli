package com.jpm.mars.rover.assignment.domain.model;

import lombok.*;

@Builder(toBuilder = true)
public record Vector2D(double x, double y) {
    public static final Vector2D NORTH_UNIT_VECTOR = new Vector2D(0.0, 1.0);
    public static final Vector2D EAST_UNIT_VECTOR = new Vector2D(1.0, 0.0);
    public static final Vector2D SOUTH_UNIT_VECTOR = new Vector2D(0.0, -1.0);
    public static final Vector2D WEST_UNIT_VECTOR = new Vector2D(-1.0, 0.0);

    public Vector2D add(@NonNull Vector2D other) {
        return this.toBuilder()
                .x(this.x + other.x)
                .y(this.y + other.y)
                .build();
    }

    public double getMagnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2D getUnitVector() {
        double magnitude = this.getMagnitude();
        if (magnitude == 0) {
            throw new ArithmeticException("No unit vector for zero vector");
        }
        return this.toBuilder()
                .x(this.x / magnitude)
                .y(this.y / magnitude)
                .build();
    }

    public Vector2D scalarMultiply(double magnitude) {
        return this.toBuilder()
                .x(this.x * magnitude)
                .y(this.y * magnitude)
                .build();
    }
}



