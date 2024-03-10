package com.jpm.mars.rover.assignment.domain.mapper;

import com.jpm.mars.rover.assignment.domain.model.Direction;
import com.jpm.mars.rover.assignment.domain.entity.Rover;
import com.jpm.mars.rover.assignment.domain.model.RoverState;
import com.jpm.mars.rover.assignment.domain.model.Vector2D;
import com.jpm.mars.rover.assignment.dto.persistence.RoverDTO;
import org.springframework.stereotype.Service;

public interface RoverPersistenceMapper {

    Rover mapRoverPersistenceToDomain(RoverDTO rover);
    RoverDTO mapRoverDomainToPersistence(Rover rover);

    @Service
    class RoverPersistenceMapperImpl implements RoverPersistenceMapper {

        @Override
        public Rover mapRoverPersistenceToDomain(RoverDTO rover) {
            return new Rover(
                    rover.getId(),
                    new RoverState(
                        new Vector2D(rover.getX(), rover.getY()),
                        new Direction(new Vector2D(rover.getDirection_x(), rover.getDirection_y()))
                    ));
        }

        @Override
        public RoverDTO mapRoverDomainToPersistence(Rover rover) {
            RoverState roverState = rover.getRoverState();
            Vector2D coordinate = roverState.coordinate();
            Vector2D directionUnitVector = roverState.direction().getUnitVector();
            return new RoverDTO(rover.getId(), coordinate.x(), coordinate.y(), directionUnitVector.x(), directionUnitVector.y());
        }
    }
}
