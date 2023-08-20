package com.mymap.trafficpredict.dto;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public enum IconType {
    AUTO;

    @JsonValue
    public String toValue() {
        switch (this) {
            case AUTO: return "Auto";
        }
        return null;
    }

    @JsonCreator
    public static IconType forValue(String value) throws IOException {
        if (value.equals("Auto")) return AUTO;
        throw new IOException("Cannot deserialize IconType");
    }
}