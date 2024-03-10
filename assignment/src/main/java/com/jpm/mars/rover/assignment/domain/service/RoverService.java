package com.jpm.mars.rover.assignment.domain.service;

import com.jpm.mars.rover.assignment.domain.command.Command;
import com.jpm.mars.rover.assignment.domain.exceptions.RoverNotFoundException;
import com.jpm.mars.rover.assignment.domain.entity.Rover;
import com.jpm.mars.rover.assignment.domain.model.RoverState;
import com.jpm.mars.rover.assignment.domain.util.Result;
import com.jpm.mars.rover.assignment.domain.repository.RoverRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface RoverService {
    Result<Rover> createRover(RoverState roverState);
    Iterable<Rover> getAllRovers();
    Result<Rover> getRoverById(String id);
    Result<Rover> executeCommand(String id, Command cmd);

    @RequiredArgsConstructor
    @Service
    class RoverServiceImpl implements RoverService {

        @NonNull private final RoverRepository roverRepository;

        @Override
        public Result<Rover> createRover(RoverState roverState) {
            Rover rover = roverRepository.save(
                    new Rover(null, roverState)
            );
            return Result.success(rover);
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

        @Override
        public Result<Rover> executeCommand(String id, Command cmd) {
            Result<Rover> roverResult = getRoverById(id);
            if (!roverResult.isSuccess()) {
                return roverResult;
            }
            Rover rover = roverResult.getValue().orElseThrow();
            cmd.Execute(rover);

            return Result.success(roverRepository.save(rover));
        }
    }

}
