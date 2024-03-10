package com.jpm.mars.rover.assignment.dto.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Entity
@AllArgsConstructor
public class RoverDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double x;
    private double y;
    private double direction_x;
    private double direction_y;
    private String status; // Setting as a string to allow flexibility to add new statuses later on without db migrations

    protected RoverDTO() {}

}
