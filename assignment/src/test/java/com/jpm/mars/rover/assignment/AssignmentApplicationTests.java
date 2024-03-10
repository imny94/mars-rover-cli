package com.jpm.mars.rover.assignment;

import com.jpm.mars.rover.assignment.domain.controller.RoverController;
import com.jpm.mars.rover.assignment.domain.entity.Rover;
import com.jpm.mars.rover.assignment.domain.model.Vector2D;
import com.jpm.mars.rover.assignment.domain.util.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
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
