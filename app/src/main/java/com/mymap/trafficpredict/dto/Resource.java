package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {
    private String type;
    private double[] bbox;
    private String id;
    private String distanceUnit;
    private String durationUnit;
    private RouteLeg[] routeLegs;
    private RoutePath routePath;
    private String trafficCongestion;
    private String trafficDataUsed;
    private double travelDistance;
    private long travelDuration;
    private long travelDurationTraffic;
    private Mode travelMode;

    @JsonProperty("__type")
    public String getType() { return type; }
    @JsonProperty("__type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("bbox")
    public double[] getBbox() { return bbox; }
    @JsonProperty("bbox")
    public void setBbox(double[] value) { this.bbox = value; }

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("distanceUnit")
    public String getDistanceUnit() { return distanceUnit; }
    @JsonProperty("distanceUnit")
    public void setDistanceUnit(String value) { this.distanceUnit = value; }

    @JsonProperty("durationUnit")
    public String getDurationUnit() { return durationUnit; }
    @JsonProperty("durationUnit")
    public void setDurationUnit(String value) { this.durationUnit = value; }

    @JsonProperty("routeLegs")
    public RouteLeg[] getRouteLegs() { return routeLegs; }
    @JsonProperty("routeLegs")
    public void setRouteLegs(RouteLeg[] value) { this.routeLegs = value; }

    @JsonProperty("routePath")
    public RoutePath getRoutePath() { return routePath; }
    @JsonProperty("routePath")
    public void setRoutePath(RoutePath value) { this.routePath = value; }

    @JsonProperty("trafficCongestion")
    public String getTrafficCongestion() { return trafficCongestion; }
    @JsonProperty("trafficCongestion")
    public void setTrafficCongestion(String value) { this.trafficCongestion = value; }

    @JsonProperty("trafficDataUsed")
    public String getTrafficDataUsed() { return trafficDataUsed; }
    @JsonProperty("trafficDataUsed")
    public void setTrafficDataUsed(String value) { this.trafficDataUsed = value; }

    @JsonProperty("travelDistance")
    public double getTravelDistance() { return travelDistance; }
    @JsonProperty("travelDistance")
    public void setTravelDistance(double value) { this.travelDistance = value; }

    @JsonProperty("travelDuration")
    public long getTravelDuration() { return travelDuration; }
    @JsonProperty("travelDuration")
    public void setTravelDuration(long value) { this.travelDuration = value; }

    @JsonProperty("travelDurationTraffic")
    public long getTravelDurationTraffic() { return travelDurationTraffic; }
    @JsonProperty("travelDurationTraffic")
    public void setTravelDurationTraffic(long value) { this.travelDurationTraffic = value; }

    @JsonProperty("travelMode")
    public Mode getTravelMode() { return travelMode; }
    @JsonProperty("travelMode")
    public void setTravelMode(Mode value) { this.travelMode = value; }
}