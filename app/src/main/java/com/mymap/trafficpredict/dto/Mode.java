package com.mymap.trafficpredict.dto;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public enum Mode {
    DRIVING;

    @JsonValue
    public String toValue() {
        switch (this) {
            case DRIVING: return "Driving";
        }
        return null;
    }

    @JsonCreator
    public static Mode forValue(String value) throws IOException {
        if (value.equals("Driving")) return DRIVING;
        throw new IOException("Cannot deserialize Mode");
    }
}