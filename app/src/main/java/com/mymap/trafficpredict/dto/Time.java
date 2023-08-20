package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Time {
    private String dateTime;
    private long offsetMinutes;

    @JsonProperty("DateTime")
    public String getDateTime() { return dateTime; }
    @JsonProperty("DateTime")
    public void setDateTime(String value) { this.dateTime = value; }

    @JsonProperty("OffsetMinutes")
    public long getOffsetMinutes() { return offsetMinutes; }
    @JsonProperty("OffsetMinutes")
    public void setOffsetMinutes(long value) { this.offsetMinutes = value; }
}