package com.jpm.mars.rover.assignment.domain.repository;

import com.jpm.mars.rover.assignment.domain.mapper.RoverPersistenceMapper;
import com.jpm.mars.rover.assignment.domain.entity.Rover;
import com.jpm.mars.rover.assignment.dto.persistence.RoverDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface RoverRepository {
    Rover save(Rover rover);
    Optional<Rover> findById(String id);
    Iterable<Rover> findAll();


    @RequiredArgsConstructor
    @Repository
    class RoverRepositoryImpl implements RoverRepository {

        @NonNull private final RoverPersistenceMapper roverPersistenceMapper;
        @NonNull private final com.jpm.mars.rover.assignment.dto.repository.RoverRepository roverPersistenceRepository;

        @Override
        public Rover save(Rover rover) {
            RoverDTO roverDTO = this.roverPersistenceRepository.save(
                    this.roverPersistenceMapper.mapRoverDomainToPersistence(rover)
            );
            return this.roverPersistenceMapper.mapRoverPersistenceToDomain(roverDTO);
        }

        @Override
        public Optional<Rover> findById(String id) {
            return this.roverPersistenceRepository.findById(id)
                    .map(this.roverPersistenceMapper::mapRoverPersistenceToDomain);
        }

        @Override
        public Iterable<Rover> findAll() {
            return StreamSupport.stream(this.roverPersistenceRepository.findAll().spliterator(), false)
                    .map(this.roverPersistenceMapper::mapRoverPersistenceToDomain)
                    .collect(Collectors.toList());
        }
    }
}
