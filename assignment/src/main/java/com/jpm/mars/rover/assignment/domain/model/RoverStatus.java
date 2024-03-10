package com.jpm.mars.rover.assignment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum RoverStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String statusName;

    private static final Map<String, RoverStatus> stringToEnum = new HashMap<>();
    static {
        for (RoverStatus rs : RoverStatus.values()) {
            stringToEnum.put(rs.statusName, rs);
        }
    }

    public static RoverStatus fromString(String value) {
        return stringToEnum.get(value);
    }
}
