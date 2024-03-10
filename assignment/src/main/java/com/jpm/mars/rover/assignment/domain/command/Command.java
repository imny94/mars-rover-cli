package com.jpm.mars.rover.assignment.domain.command;

import com.jpm.mars.rover.assignment.domain.entity.Rover;
import com.jpm.mars.rover.assignment.domain.model.Vector2D;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public interface Command {
    void Execute(Rover rover);

    @RequiredArgsConstructor
    class MoveBackwards implements Command {

        @NonNull
        private final Double magnitude;

        @Override
        public void Execute(Rover rover) {
            Vector2D moveVector = rover.getRoverState().direction().getUnitVector().scalarMultiply(-this.magnitude);
            rover.move(moveVector);
        }
    }

    @RequiredArgsConstructor
    public class MoveForward implements Command {

        @NonNull private final Double magnitude;

        @Override
        public void Execute(Rover rover) {
            Vector2D moveVector = rover.getRoverState().direction().getUnitVector().scalarMultiply(this.magnitude);
            rover.move(moveVector);
        }
    }

    @RequiredArgsConstructor
    class RotateAntiClockwise implements Command {

        @NonNull private final Double angle;

        @Override
        public void Execute(Rover rover) {
            rover.rotateClockwise((360 - angle) % 360);
        }
    }

    @RequiredArgsConstructor
    class RotateClockwise implements Command {

        @NonNull private final Double angle;

        @Override
        public void Execute(Rover rover) {
            rover.rotateClockwise(this.angle);
        }
    }

    class RotateLeft implements Command {

        @Override
        public void Execute(Rover rover) {
            new RotateAntiClockwise(90.0).Execute(rover);
        }
    }

    class RotateRight implements Command {

        @Override
        public void Execute(Rover rover) {
            new RotateClockwise(90.0).Execute(rover);
        }
    }
}

