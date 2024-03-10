package com.jpm.mars.rover.assignment.domain.util;

import com.jpm.mars.rover.assignment.domain.model.Vector2D;

public class VectorConverter {
    public static Vector2D degreesToUnitVector(double degrees) {
        degrees = degrees % 360;
        if (degrees < 0) {
            // Ensure the angle is positive (within the range [0, 360))
            degrees += 360;
        }
        // Convert degrees to radians
        double radians = Math.toRadians(degrees);

        // Calculate components of the vector
        double y = Math.cos(radians);
        double x = Math.sin(radians);

        return new Vector2D(x, y);
    }

    public static double vectorToDegrees(Vector2D vector) {
        // Calculate the angle using arctangent (atan2)
        double angleInRadians = Math.atan2(vector.x(), vector.y());

        // Convert radians to degrees
        double angleInDegrees = Math.toDegrees(angleInRadians);

        // Ensure angle is positive (between 0 and 360)
        if (angleInDegrees < 0) {
            angleInDegrees += 360;
        }

        return angleInDegrees;
    }
}

