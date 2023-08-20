package com.mymap.trafficpredict.dto;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public enum RoadType {
    ARTERIAL, HIGHWAY, LIMITED_ACCESS_HIGHWAY, MAJOR_ROAD, RAMP, STREET;

    @JsonValue
    public String toValue() {
        switch (this) {
            case ARTERIAL: return "Arterial";
            case HIGHWAY: return "Highway";
            case LIMITED_ACCESS_HIGHWAY: return "LimitedAccessHighway";
            case MAJOR_ROAD: return "MajorRoad";
            case RAMP: return "Ramp";
            case STREET: return "Street";
        }
        return null;
    }

    @JsonCreator
    public static RoadType forValue(String value) throws IOException {
        if (value.equals("Arterial")) return ARTERIAL;
        if (value.equals("Highway")) return HIGHWAY;
        if (value.equals("LimitedAccessHighway")) return LIMITED_ACCESS_HIGHWAY;
        if (value.equals("MajorRoad")) return MAJOR_ROAD;
        if (value.equals("Ramp")) return RAMP;
        if (value.equals("Street")) return STREET;
        throw new IOException("Cannot deserialize RoadType");
    }
}