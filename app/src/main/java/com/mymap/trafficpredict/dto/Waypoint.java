package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Waypoint {
    private Type type;
    private double[] coordinates;
    private String description;
    private boolean isVia;
    private String locationIdentifier;
    private long routePathIndex;

    @JsonProperty("type")
    public Type getType() { return type; }
    @JsonProperty("type")
    public void setType(Type value) { this.type = value; }

    @JsonProperty("coordinates")
    public double[] getCoordinates() { return coordinates; }
    @JsonProperty("coordinates")
    public void setCoordinates(double[] value) { this.coordinates = value; }

    @JsonProperty("description")
    public String getDescription() { return description; }
    @JsonProperty("description")
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("isVia")
    public boolean getIsVia() { return isVia; }
    @JsonProperty("isVia")
    public void setIsVia(boolean value) { this.isVia = value; }

    @JsonProperty("locationIdentifier")
    public String getLocationIdentifier() { return locationIdentifier; }
    @JsonProperty("locationIdentifier")
    public void setLocationIdentifier(String value) { this.locationIdentifier = value; }

    @JsonProperty("routePathIndex")
    public long getRoutePathIndex() { return routePathIndex; }
    @JsonProperty("routePathIndex")
    public void setRoutePathIndex(long value) { this.routePathIndex = value; }
}