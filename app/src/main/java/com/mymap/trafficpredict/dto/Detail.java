package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Detail {
    private long compassDegrees;
    private long[] endPathIndices;
    private String[] locationCodes;
    private String maneuverType;
    private Mode mode;
    private String[] names;
    private RoadType roadType;
    private long[] startPathIndices;

    @JsonProperty("compassDegrees")
    public long getCompassDegrees() { return compassDegrees; }
    @JsonProperty("compassDegrees")
    public void setCompassDegrees(long value) { this.compassDegrees = value; }

    @JsonProperty("endPathIndices")
    public long[] getEndPathIndices() { return endPathIndices; }
    @JsonProperty("endPathIndices")
    public void setEndPathIndices(long[] value) { this.endPathIndices = value; }

    @JsonProperty("locationCodes")
    public String[] getLocationCodes() { return locationCodes; }
    @JsonProperty("locationCodes")
    public void setLocationCodes(String[] value) { this.locationCodes = value; }

    @JsonProperty("maneuverType")
    public String getManeuverType() { return maneuverType; }
    @JsonProperty("maneuverType")
    public void setManeuverType(String value) { this.maneuverType = value; }

    @JsonProperty("mode")
    public Mode getMode() { return mode; }
    @JsonProperty("mode")
    public void setMode(Mode value) { this.mode = value; }

    @JsonProperty("names")
    public String[] getNames() { return names; }
    @JsonProperty("names")
    public void setNames(String[] value) { this.names = value; }

    @JsonProperty("roadType")
    public RoadType getRoadType() { return roadType; }
    @JsonProperty("roadType")
    public void setRoadType(RoadType value) { this.roadType = value; }

    @JsonProperty("startPathIndices")
    public long[] getStartPathIndices() { return startPathIndices; }
    @JsonProperty("startPathIndices")
    public void setStartPathIndices(long[] value) { this.startPathIndices = value; }
}