package com.jpm.mars.rover.assignment;

import com.jpm.mars.rover.assignment.domain.controller.RoverController;
import com.jpm.mars.rover.assignment.domain.entity.Rover;
import com.jpm.mars.rover.assignment.domain.model.RoverStatus;
import com.jpm.mars.rover.assignment.domain.model.Vector2D;
import com.jpm.mars.rover.assignment.domain.util.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AssignmentApplicationTests {

	@Autowired
	private RoverController roverController;

	@Test
	void contextLoads() {
	}

	@Test
	void singleRoverIntegrationTest() {
		Result<Rover> createRes = roverController.createRover(3, 4, "N");
		assertTrue(createRes.isSuccess());
		assertTrue(createRes.getException().isEmpty());
		assertNotNull(createRes.getValue().orElse(null));

		Rover rover = createRes.getValue().orElseThrow();
		Assertions.assertThat(roverController.getAllRovers()).usingRecursiveFieldByFieldElementComparator().containsExactly(rover);

		Result<Rover> roverResult = roverController.executeCommand(rover.getId(), "f");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(3, 5));
		testRoverDirection(rover, new Vector2D(0, 1));

		roverResult = roverController.executeCommand(rover.getId(), "f");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(3, 6));
		testRoverDirection(rover, new Vector2D(0, 1));

		roverResult = roverController.executeCommand(rover.getId(), "r");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(3, 6));
		testRoverDirection(rover, new Vector2D(1, 0));

		roverResult = roverController.executeCommand(rover.getId(), "f");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(4, 6));
		testRoverDirection(rover, new Vector2D(1, 0));

		roverResult = roverController.executeCommand(rover.getId(), "f");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 6));
		testRoverDirection(rover, new Vector2D(1, 0));

		roverResult = roverController.executeCommand(rover.getId(), "l");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 6));
		testRoverDirection(rover, new Vector2D(0, 1));

		roverResult = roverController.executeCommand(rover.getId(), "l");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 6));
		testRoverDirection(rover, new Vector2D(-1, 0));

		roverResult = roverController.executeCommand(rover.getId(), "l");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 6));
		testRoverDirection(rover, new Vector2D(0, -1));

		roverResult = roverController.executeCommand(rover.getId(), "b");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 7));
		testRoverDirection(rover, new Vector2D(0, -1));

		roverResult = roverController.executeCommand(rover.getId(), "b");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 8));
		testRoverDirection(rover, new Vector2D(0, -1));
	}

	@Test
	void multipleRoverIntegrationTest() {
		List<Rover> rovers = new ArrayList<>();
		Result<Rover> createRes = roverController.createRover(3, 4, "N");
		assertTrue(createRes.isSuccess());
		assertTrue(createRes.getException().isEmpty());
		assertNotNull(createRes.getValue().orElse(null));
		Rover rover = createRes.getValue().orElseThrow();
		rovers.add(rover);

		createRes = roverController.createRover(3, 5, "S");
		assertTrue(createRes.isSuccess());
		assertTrue(createRes.getException().isEmpty());
		assertNotNull(createRes.getValue().orElse(null));
		rover = createRes.getValue().orElseThrow();
		rovers.add(rover);

		createRes = roverController.createRover(3, 3, "N");
		assertTrue(createRes.isSuccess());
		assertTrue(createRes.getException().isEmpty());
		assertNotNull(createRes.getValue().orElse(null));
		rover = createRes.getValue().orElseThrow();
		rovers.add(rover);

		createRes = roverController.createRover(2, 4, "E");
		assertTrue(createRes.isSuccess());
		assertTrue(createRes.getException().isEmpty());
		assertNotNull(createRes.getValue().orElse(null));
		rover = createRes.getValue().orElseThrow();
		rovers.add(rover);

		// Test the failure case where we try to create a rover on a coordinate with another rover
		createRes = roverController.createRover(3, 4, "W");
		assertFalse(createRes.isSuccess());
		assertTrue(createRes.getValue().isEmpty());
		assertNotNull(createRes.getException().orElse(null));

		createRes = roverController.createRover(4, 4, "W");
		assertTrue(createRes.isSuccess());
		assertTrue(createRes.getException().isEmpty());
		assertNotNull(createRes.getValue().orElse(null));
		rover = createRes.getValue().orElseThrow();
		rovers.add(rover);

		Assertions.assertThat(roverController.getAllRovers()).usingRecursiveFieldByFieldElementComparator().containsAll(rovers);

		// First rover to move up should fail with exception since it would result in a collision
		Result<Rover> roverResult = roverController.executeCommand(rovers.getFirst().getId(), "f");
		assertFalse(roverResult.isSuccess());
		assertNotNull(roverResult.getException().orElse(null));
		assertEquals(
				RoverStatus.INACTIVE,
				StreamSupport
						.stream(roverController.getAllRovers().spliterator(), false)
						.filter(r -> r.getId().equals(rovers.getFirst().getId()))
						.findFirst().orElseThrow()
						.getRoverStatus()
		);

		// Subsequent requests to move the first rovers should fail
		roverResult = roverController.executeCommand(rovers.getFirst().getId(), "f");
		assertFalse(roverResult.isSuccess());
		assertNotNull(roverResult.getException().orElse(null));

		// Move the second rover to make sure that it doesn't have any issues
		roverResult = roverController.executeCommand(rovers.get(1).getId(), "r");
		System.out.println(rovers.get(1));
		System.out.println(roverResult.getValue().orElseThrow());
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(3, 5));
		testRoverDirection(rover, new Vector2D(-1, 0));

		roverResult = roverController.executeCommand(rovers.get(1).getId(), "l");
		System.out.println(rovers.get(1));
		System.out.println(roverResult.getValue().orElseThrow());
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(3, 5));
		testRoverDirection(rover, new Vector2D(0, -1));

		roverResult = roverController.executeCommand(rovers.get(1).getId(), "l");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(3, 5));
		testRoverDirection(rover, new Vector2D(1, 0));

		roverResult = roverController.executeCommand(rovers.get(1).getId(), "l");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(3, 5));
		testRoverDirection(rover, new Vector2D(0, 1));

		roverResult = roverController.executeCommand(rover.getId(), "f");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(3, 6));
		testRoverDirection(rover, new Vector2D(0, 1));

		roverResult = roverController.executeCommand(rover.getId(), "r");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(3, 6));
		testRoverDirection(rover, new Vector2D(1, 0));

		roverResult = roverController.executeCommand(rover.getId(), "f");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(4, 6));
		testRoverDirection(rover, new Vector2D(1, 0));

		roverResult = roverController.executeCommand(rover.getId(), "f");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 6));
		testRoverDirection(rover, new Vector2D(1, 0));

		roverResult = roverController.executeCommand(rover.getId(), "l");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 6));
		testRoverDirection(rover, new Vector2D(0, 1));

		roverResult = roverController.executeCommand(rover.getId(), "l");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 6));
		testRoverDirection(rover, new Vector2D(-1, 0));

		roverResult = roverController.executeCommand(rover.getId(), "l");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 6));
		testRoverDirection(rover, new Vector2D(0, -1));

		roverResult = roverController.executeCommand(rover.getId(), "b");
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 7));
		testRoverDirection(rover, new Vector2D(0, -1));

		roverResult = roverController.executeCommand(rover.getId(), "b");
		System.out.println(roverResult.getException());
		System.out.println(roverController.getAllRovers());
		assertTrue(roverResult.isSuccess());
		assertNotNull(roverResult.getValue().orElse(null));
		rover = roverResult.getValue().orElseThrow();
		testRoverCoordinates(rover, new Vector2D(5, 8));
		testRoverDirection(rover, new Vector2D(0, -1));
	}

	private void testRoverCoordinates(Rover rover, Vector2D expectedCoordinates) {
		compareVectors(expectedCoordinates, rover.getRoverState().coordinate());
	}

	private void compareVectors(Vector2D vec, Vector2D other) {
		assertEquals(vec.x(), other.x(), 0.00001);
		assertEquals(vec.y(), other.y(), 0.00001);
	}

	private void testRoverDirection(Rover rover, Vector2D expectedUnitVector) {
		compareVectors(expectedUnitVector, rover.getRoverState().direction().getUnitVector());
	}

}
