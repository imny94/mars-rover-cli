package com.jpm.mars.rover.assignment.domain.model;

import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record RoverState(
        @NonNull Vector2D coordinate,
        @NonNull Direction direction
) {
}

