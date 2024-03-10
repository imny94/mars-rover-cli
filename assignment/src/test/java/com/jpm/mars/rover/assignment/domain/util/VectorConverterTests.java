package com.jpm.mars.rover.assignment.domain.util;

import com.jpm.mars.rover.assignment.domain.model.Vector2D;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class VectorConverterTests {
    @Test
    public void testDegreesToUnitVector_0Degrees() {
        Vector2D vector = VectorConverter.degreesToUnitVector(0);
        assertEquals(0.0, vector.x(), 0.0001);
        assertEquals(1.0, vector.y(), 0.0001);
        assertEquals(1.0, vector.getMagnitude(), 0.0001);
    }

    @Test
    public void testDegreesToUnitVector_90Degrees() {
        Vector2D vector = VectorConverter.degreesToUnitVector(90);
        assertEquals(1.0, vector.x(), 0.0001);
        assertEquals(0.0, vector.y(), 0.0001);
        assertEquals(1.0, vector.getMagnitude(), 0.0001);
    }

    @Test
    public void testDegreesToUnitVector_180Degrees() {
        Vector2D vector = VectorConverter.degreesToUnitVector(180);
        assertEquals(0.0, vector.x(), 0.0001);
        assertEquals(-1.0, vector.y(), 0.0001);
        assertEquals(1.0, vector.getMagnitude(), 0.0001);
    }

    @Test
    public void testDegreesToUnitVector_270Degrees() {
        Vector2D vector = VectorConverter.degreesToUnitVector(270);
        assertEquals(-1.0, vector.x(), 0.0001);
        assertEquals(0.0, vector.y(), 0.0001);
        assertEquals(1.0, vector.getMagnitude(), 0.0001);
    }

    @Test
    public void testDegreesToUnitVector_360Degrees() {
        Vector2D vector = VectorConverter.degreesToUnitVector(360);
        assertEquals(0.0, vector.x(), 0.0001);
        assertEquals(1.0, vector.y(), 0.0001);
        assertEquals(1.0, vector.getMagnitude(), 0.0001);
    }

    @Test
    public void testDegreesToUnitVector_405Degrees() {
        Vector2D vector = VectorConverter.degreesToUnitVector(405);
        assertEquals(1 / Math.sqrt(2), vector.x(), 0.0001);
        assertEquals(1 / Math.sqrt(2), vector.y(), 0.0001);
        assertEquals(1.0, vector.getMagnitude(), 0.0001);
    }

    @Test
    public void testvectorToDegrees_90Degrees() {
        double degrees = VectorConverter.vectorToDegrees(new Vector2D(1, 0));
        assertEquals(90, degrees, 0.0001);
    }

    @Test
    public void testvectorToDegrees_0Degrees() {
        double degrees = VectorConverter.vectorToDegrees(new Vector2D(0, 1));
        assertEquals(0, degrees, 0.0001);
    }

    @Test
    public void testvectorToDegrees_270Degrees() {
        double degrees = VectorConverter.vectorToDegrees(new Vector2D(-1, 0));
        assertEquals(270, degrees, 0.0001);
    }

    @Test
    public void testvectorToDegrees_180Degrees() {
        double degrees = VectorConverter.vectorToDegrees(new Vector2D(0, -1));
        assertEquals(180, degrees, 0.0001);
    }

    @Test
    public void testvectorToDegrees_45Degrees() {
        double degrees = VectorConverter.vectorToDegrees(new Vector2D(1, 1));
        assertEquals(45, degrees, 0.0001);
    }
}

