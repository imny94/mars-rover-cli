package com.jpm.mars.rover.assignment.domain.entity;

import com.jpm.mars.rover.assignment.domain.model.RoverState;
import com.jpm.mars.rover.assignment.domain.model.RoverStatus;
import com.jpm.mars.rover.assignment.domain.model.Vector2D;
import com.jpm.mars.rover.assignment.domain.util.VectorConverter;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Rover {
    private final String id; // This might be null if it is just created without an id
    @NonNull private RoverState roverState;
    @NonNull private RoverStatus roverStatus;

    public void move(@NonNull Vector2D vector) {
        if (roverStatus.equals(RoverStatus.INACTIVE)) {
            return;
        }
        this.roverState = this.roverState
                .toBuilder()
                .coordinate(this.roverState.coordinate().add(vector))
                .build();
    }

    public void rotateClockwise(double angle) {
        if (roverStatus.equals(RoverStatus.INACTIVE)) {
            return;
        }
        this.roverState = this.roverState
                .toBuilder()
                .direction(new com.jpm.mars.rover.assignment.domain.model.Direction(
                        VectorConverter.degreesToUnitVector(
                                VectorConverter.vectorToDegrees(roverState.direction().getUnitVector()) + angle
                        )))
                .build();
    }

    public void deactivateRover() {
        roverStatus = RoverStatus.INACTIVE;
    }

    @Override
    public String toString() {
        return "Rover{" +
                "id='" + id + '\'' +
                ", coordinates=(" + roverState.coordinate().x() + "," + roverState.coordinate().y() + ")" +
                ", direction=" + roverState.direction().getName() +
                ", status=" + roverStatus +
                '}';
    }
}
