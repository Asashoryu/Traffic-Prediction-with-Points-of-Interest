package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteLeg {
    private ActualEnd actualEnd;
    private ActualEnd actualStart;
    private Object[] alternateVias;
    private String description;
    private EndLocation endLocation;
    private String endTime;
    private ItineraryItem[] itineraryItems;
    private String routeRegion;
    private RouteSubLeg[] routeSubLegs;
    private StartLocation startLocation;
    private String startTime;
    private double travelDistance;
    private long travelDuration;
    private Mode travelMode;

    @JsonProperty("actualEnd")
    public ActualEnd getActualEnd() { return actualEnd; }
    @JsonProperty("actualEnd")
    public void setActualEnd(ActualEnd value) { this.actualEnd = value; }

    @JsonProperty("actualStart")
    public ActualEnd getActualStart() { return actualStart; }
    @JsonProperty("actualStart")
    public void setActualStart(ActualEnd value) { this.actualStart = value; }

    @JsonProperty("alternateVias")
    public Object[] getAlternateVias() { return alternateVias; }
    @JsonProperty("alternateVias")
    public void setAlternateVias(Object[] value) { this.alternateVias = value; }

    @JsonProperty("description")
    public String getDescription() { return description; }
    @JsonProperty("description")
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("endLocation")
    public EndLocation getEndLocation() { return endLocation; }
    @JsonProperty("endLocation")
    public void setEndLocation(EndLocation value) { this.endLocation = value; }

    @JsonProperty("endTime")
    public String getEndTime() { return endTime; }
    @JsonProperty("endTime")
    public void setEndTime(String value) { this.endTime = value; }

    @JsonProperty("itineraryItems")
    public ItineraryItem[] getItineraryItems() { return itineraryItems; }
    @JsonProperty("itineraryItems")
    public void setItineraryItems(ItineraryItem[] value) { this.itineraryItems = value; }

    @JsonProperty("routeRegion")
    public String getRouteRegion() { return routeRegion; }
    @JsonProperty("routeRegion")
    public void setRouteRegion(String value) { this.routeRegion = value; }

    @JsonProperty("routeSubLegs")
    public RouteSubLeg[] getRouteSubLegs() { return routeSubLegs; }
    @JsonProperty("routeSubLegs")
    public void setRouteSubLegs(RouteSubLeg[] value) { this.routeSubLegs = value; }

    @JsonProperty("startLocation")
    public StartLocation getStartLocation() { return startLocation; }
    @JsonProperty("startLocation")
    public void setStartLocation(StartLocation value) { this.startLocation = value; }

    @JsonProperty("startTime")
    public String getStartTime() { return startTime; }
    @JsonProperty("startTime")
    public void setStartTime(String value) { this.startTime = value; }

    @JsonProperty("travelDistance")
    public double getTravelDistance() { return travelDistance; }
    @JsonProperty("travelDistance")
    public void setTravelDistance(double value) { this.travelDistance = value; }

    @JsonProperty("travelDuration")
    public long getTravelDuration() { return travelDuration; }
    @JsonProperty("travelDuration")
    public void setTravelDuration(long value) { this.travelDuration = value; }

    @JsonProperty("travelMode")
    public Mode getTravelMode() { return travelMode; }
    @JsonProperty("travelMode")
    public void setTravelMode(Mode value) { this.travelMode = value; }
}