package com.jpm.mars.rover.assignment.domain.model;

import com.jpm.mars.rover.assignment.domain.util.VectorConverter;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.AbstractMap;
import java.util.Map;

import static com.jpm.mars.rover.assignment.domain.model.Vector2D.*;

/*
Sets direction of the rover, uses vectors to allow more flexibility to add more directions
on top of the 4 cardinal directions in the future
 */
@Getter
public final class Direction {
    private final Vector2D unitVector;

    public Direction(com.jpm.mars.rover.assignment.domain.model.Vector2D unitVector) {
        if (Math.abs(unitVector.getMagnitude() - 1.0) > 0.00001) {
            throw new IllegalArgumentException("Invalid unit vector with non-1 magnitude");
        }
        this.unitVector = unitVector;
    }

    private static final Map<Double, String> DIRECTIONS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(0.0, "North"),
            new AbstractMap.SimpleEntry<>(90.0, "East"),
            new AbstractMap.SimpleEntry<>(180.0, "South"),
            new AbstractMap.SimpleEntry<>(270.0, "West")
    );


    public String getName() {
        double degrees = VectorConverter.vectorToDegrees(this.unitVector);
        return DIRECTIONS.getOrDefault(
                degrees,
                String.format("%s degrees", degrees)
        );
    }
}

