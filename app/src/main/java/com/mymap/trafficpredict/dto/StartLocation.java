package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartLocation {
    private double[] bbox;
    private String name;
    private ActualEnd point;
    private StartLocationAddress address;
    private String confidence;
    private String entityType;
    private GeocodePoint[] geocodePoints;
    private String[] matchCodes;

    @JsonProperty("bbox")
    public double[] getBbox() { return bbox; }
    @JsonProperty("bbox")
    public void setBbox(double[] value) { this.bbox = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("point")
    public ActualEnd getPoint() { return point; }
    @JsonProperty("point")
    public void setPoint(ActualEnd value) { this.point = value; }

    @JsonProperty("address")
    public StartLocationAddress getAddress() { return address; }
    @JsonProperty("address")
    public void setAddress(StartLocationAddress value) { this.address = value; }

    @JsonProperty("confidence")
    public String getConfidence() { return confidence; }
    @JsonProperty("confidence")
    public void setConfidence(String value) { this.confidence = value; }

    @JsonProperty("entityType")
    public String getEntityType() { return entityType; }
    @JsonProperty("entityType")
    public void setEntityType(String value) { this.entityType = value; }

    @JsonProperty("geocodePoints")
    public GeocodePoint[] getGeocodePoints() { return geocodePoints; }
    @JsonProperty("geocodePoints")
    public void setGeocodePoints(GeocodePoint[] value) { this.geocodePoints = value; }

    @JsonProperty("matchCodes")
    public String[] getMatchCodes() { return matchCodes; }
    @JsonProperty("matchCodes")
    public void setMatchCodes(String[] value) { this.matchCodes = value; }
}