package com.mymap.trafficpredict.dto;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum CompassDirection {
    EAST, NORTHWEST, SOUTH, SOUTHEAST, SOUTHWEST, WEST;

    @JsonValue
    public String toValue() {
        switch (this) {
            case EAST: return "east";
            case NORTHWEST: return "northwest";
            case SOUTH: return "south";
            case SOUTHEAST: return "southeast";
            case SOUTHWEST: return "southwest";
            case WEST: return "west";
        }
        return null;
    }

    @JsonCreator
    public static CompassDirection forValue(String value) throws IOException {
        if (value.equals("east")) return EAST;
        if (value.equals("northwest")) return NORTHWEST;
        if (value.equals("south")) return SOUTH;
        if (value.equals("southeast")) return SOUTHEAST;
        if (value.equals("southwest")) return SOUTHWEST;
        if (value.equals("west")) return WEST;
        throw new IOException("Cannot deserialize CompassDirection");
    }
}