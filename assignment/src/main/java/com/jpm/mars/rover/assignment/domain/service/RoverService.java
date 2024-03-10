package com.jpm.mars.rover.assignment.domain.service;

import com.jpm.mars.rover.assignment.domain.command.Command;
import com.jpm.mars.rover.assignment.domain.exceptions.CoordinateNotAvailableException;
import com.jpm.mars.rover.assignment.domain.exceptions.DeactivatedRoverException;
import com.jpm.mars.rover.assignment.domain.exceptions.InvalidCommandException;
import com.jpm.mars.rover.assignment.domain.exceptions.RoverNotFoundException;
import com.jpm.mars.rover.assignment.domain.entity.Rover;
import com.jpm.mars.rover.assignment.domain.model.RoverState;
import com.jpm.mars.rover.assignment.domain.model.RoverStatus;
import com.jpm.mars.rover.assignment.domain.util.Result;
import com.jpm.mars.rover.assignment.domain.repository.RoverRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface RoverService {
    Result<Rover> createRover(RoverState roverState);
    Iterable<Rover> getAllRovers();
    Result<Rover> getRoverById(String id);
    Result<Rover> executeCommand(String id, Command cmd);

    @RequiredArgsConstructor
    @Service
    class RoverServiceImpl implements RoverService {

        @NonNull private final RoverRepository roverRepository;

        @Transactional
        @Override
        public Result<Rover> createRover(RoverState roverState) {
            boolean coordinateAvailable = getAllRoversStream()
                    .noneMatch((rover -> rover.getRoverState().coordinate().equals(roverState.coordinate())));
            if (!coordinateAvailable) {
                return Result.failure(new CoordinateNotAvailableException(String.format("Coordinate (%s,%s) has been taken and is not available", roverState.coordinate().x(), roverState.coordinate().y())));
            }
            Rover rover = roverRepository.save(
                    new Rover(null, roverState, RoverStatus.ACTIVE)
            );
            return Result.success(rover);
        }

        private Stream<Rover> getAllRoversStream() {
            return StreamSupport
                    .stream(getAllRovers().spliterator(), false);
        }

        @Override
        public Iterable<Rover> getAllRovers() {
            return roverRepository.findAll();
        }

        @Override
        public Result<Rover> getRoverById(String id) {
            Optional<Rover> optionalRover = roverRepository.findById(id);
            return optionalRover
                    .map(Result::success)
                    .orElseGet(() -> Result.failure(
                            new RoverNotFoundException(String.format("Failed to find rover with id: %s", id))));
        }

        @Transactional
        @Override
        public Result<Rover> executeCommand(String id, Command cmd) {
            Result<Rover> roverResult = getRoverById(id);
            if (!roverResult.isSuccess()) {
                return roverResult;
            }
            Rover rover = roverResult.getValue().orElseThrow();
            if (rover.getRoverStatus().equals(RoverStatus.INACTIVE)) {
                return Result.failure(new DeactivatedRoverException(String.format("Command: %s ignored as rover: %s is deactivated", cmd, id)));
            }

            // we want to check if executing the command would put the rover in an inconsistent state
            Rover testRover = new Rover(null, rover.getRoverState(), RoverStatus.ACTIVE);
            cmd.Execute(testRover);
            boolean hasCollision = getAllRoversStream()
                    .filter(r -> !r.getId().equals(rover.getId()))
                    .anyMatch(r -> testRover.getRoverState().coordinate().equals(r.getRoverState().coordinate()));
            if (hasCollision) {
                rover.deactivateRover();
                roverRepository.save(rover);
                return Result.failure(new InvalidCommandException(String.format("Command: %s resulted in rover entering an invalid state, rover: %s has been deactivated.", cmd, id)));
            }

            cmd.Execute(rover);
            return Result.success(roverRepository.save(rover));
        }
    }

}
