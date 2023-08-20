package com.mymap.trafficpredict.dto;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public enum Type {
    POINT;

    @JsonValue
    public String toValue() {
        switch (this) {
            case POINT: return "Point";
        }
        return null;
    }

    @JsonCreator
    public static Type forValue(String value) throws IOException {
        if (value.equals("Point")) return POINT;
        throw new IOException("Cannot deserialize Type");
    }
}
