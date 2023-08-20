package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteSubLeg {
    private Waypoint endWaypoint;
    private Waypoint startWaypoint;
    private double travelDistance;
    private long travelDuration;

    @JsonProperty("endWaypoint")
    public Waypoint getEndWaypoint() { return endWaypoint; }
    @JsonProperty("endWaypoint")
    public void setEndWaypoint(Waypoint value) { this.endWaypoint = value; }

    @JsonProperty("startWaypoint")
    public Waypoint getStartWaypoint() { return startWaypoint; }
    @JsonProperty("startWaypoint")
    public void setStartWaypoint(Waypoint value) { this.startWaypoint = value; }

    @JsonProperty("travelDistance")
    public double getTravelDistance() { return travelDistance; }
    @JsonProperty("travelDistance")
    public void setTravelDistance(double value) { this.travelDistance = value; }

    @JsonProperty("travelDuration")
    public long getTravelDuration() { return travelDuration; }
    @JsonProperty("travelDuration")
    public void setTravelDuration(long value) { this.travelDuration = value; }
}