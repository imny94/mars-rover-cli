package com.jpm.mars.rover.assignment.domain.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class Vector2DTests {
    @Test
    public void testAdd() {
        Vector2D vector = new Vector2D(3, 4);
        Vector2D other = new Vector2D(0, 1);
        Vector2D result = vector.add(other);
        assertEquals(3, result.x());
        assertEquals(5, result.y());
        assertEquals(Math.sqrt(34), result.getMagnitude(), 0.0001);
    }
}
