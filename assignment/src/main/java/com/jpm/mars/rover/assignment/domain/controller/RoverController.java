package com.jpm.mars.rover.assignment.domain.controller;

import com.jpm.mars.rover.assignment.domain.command.Command;
import com.jpm.mars.rover.assignment.domain.exceptions.InvalidDirectionException;
import com.jpm.mars.rover.assignment.domain.exceptions.UnsupportedUserCommandException;
import com.jpm.mars.rover.assignment.domain.model.Direction;
import com.jpm.mars.rover.assignment.domain.entity.Rover;
import com.jpm.mars.rover.assignment.domain.model.RoverState;
import com.jpm.mars.rover.assignment.domain.model.Vector2D;
import com.jpm.mars.rover.assignment.domain.service.RoverService;
import com.jpm.mars.rover.assignment.domain.util.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Controller;

import java.util.*;

import static com.jpm.mars.rover.assignment.domain.model.Vector2D.*;

public interface RoverController {
    Result<Rover> createRover(double xCoordinate, double yCoordinate, String direction);
    Iterable<Rover> getAllRovers();
    Result<Rover> executeCommand(String roverId, String cmd);

    @Controller
    @AllArgsConstructor
    class RoverControllerImpl implements RoverController {
        private final Map<String, Vector2D> directionMap = Map.ofEntries(
                new AbstractMap.SimpleImmutableEntry<>("N", NORTH_UNIT_VECTOR),
                new AbstractMap.SimpleImmutableEntry<>("E", EAST_UNIT_VECTOR),
                new AbstractMap.SimpleImmutableEntry<>("S", SOUTH_UNIT_VECTOR),
                new AbstractMap.SimpleImmutableEntry<>("W", WEST_UNIT_VECTOR)
        );


        @NonNull private RoverService roverService;

        @Override
        public Result<Rover> createRover(double xCoordinate, double yCoordinate, String cardinalDirection) {
            cardinalDirection = cardinalDirection.toUpperCase();
            if (!directionMap.containsKey(cardinalDirection)) {
                return Result.failure(new InvalidDirectionException(String.format("Invalid direction `%s` provided, accepted values are: (N, S, E, W)", cardinalDirection)));
            }
            Vector2D direction = directionMap.get(cardinalDirection);
            return roverService.createRover(new RoverState(
                    new Vector2D(xCoordinate, yCoordinate),
                    new Direction(new Vector2D(direction.x(), direction.y()))
            ));
        }

        @Override
        public Iterable<Rover> getAllRovers() {
            return roverService.getAllRovers();
        }

        @Override
        public Result<Rover> executeCommand(String roverId, String cmd) {
            Command command;
            switch (cmd) {
                case "f":
                    command = new Command.MoveForward(1.0);
                    break;
                case "b":
                    command = new Command.MoveBackwards(1.0);
                    break;
                case "r":
                    command = new Command.RotateRight();
                    break;
                case "l":
                    command = new Command.RotateLeft();
                    break;
                default:
                    return Result.failure(new UnsupportedUserCommandException(String.format("Invalid Command: %s, ignoring command", cmd)));
            }
            return roverService.executeCommand(roverId, command);
        }
    }
}
