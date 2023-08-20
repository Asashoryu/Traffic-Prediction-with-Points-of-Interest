package com.mymap.trafficpredict.dto;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public enum SideOfStreet {
    UNKNOWN, RIGHT, LEFT;

    @JsonValue
    public String toValue() {
        switch (this) {
            case UNKNOWN: return "Unknown";
            case RIGHT: return "Right";
            case LEFT: return "Left";
        }
        return null;
    }

    @JsonCreator
    public static SideOfStreet forValue(String value) throws IOException {
        if (value.equals("Unknown")) return UNKNOWN;
        if (value.equals("Right")) return RIGHT;
        if (value.equals("Left")) return LEFT;
        throw new IOException("Cannot deserialize SideOfStreet");
    }
}
