package com.jpm.mars.rover.assignment.dto.repository;

import com.jpm.mars.rover.assignment.dto.persistence.RoverDTO;
import org.springframework.data.repository.CrudRepository;

public interface RoverRepository extends CrudRepository<RoverDTO, String> {
}
