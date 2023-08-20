package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualEnd {
    private Type type;
    private double[] coordinates;

    @JsonProperty("type")
    public Type getType() { return type; }
    @JsonProperty("type")
    public void setType(Type value) { this.type = value; }

    @JsonProperty("coordinates")
    public double[] getCoordinates() { return coordinates; }
    @JsonProperty("coordinates")
    public void setCoordinates(double[] value) { this.coordinates = value; }



}