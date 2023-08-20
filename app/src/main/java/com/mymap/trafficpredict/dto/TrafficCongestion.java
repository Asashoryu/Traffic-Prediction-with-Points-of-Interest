package com.mymap.trafficpredict.dto;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public enum TrafficCongestion {
    MINOR, MODERATE, NONE, UNKNOWN, MILD, MEDIUM, HEAVY;

    @JsonValue
    public String toValue() {
        switch (this) {
            case MINOR: return "Minor";
            case MODERATE: return "Moderate";
            case NONE: return "None";
            case UNKNOWN: return "Unknown";
            case MILD: return "Mild";
            case MEDIUM: return "Medium";
            case HEAVY: return "Heavy";
        }
        return null;
    }

    @JsonCreator
    public static TrafficCongestion forValue(String value) throws IOException {
        if (value.equals("Minor")) return MINOR;
        if (value.equals("Moderate")) return MODERATE;
        if (value.equals("None")) return NONE;
        if (value.equals("Unknown")) return UNKNOWN;
        if (value.equals("Mild")) return MILD;
        if (value.equals("Medium")) return MEDIUM;
        if (value.equals("Heavy")) return HEAVY;
        throw new IOException("Cannot deserialize TrafficCongestion");
    }
}