package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItineraryItem {
    private String compassDirection;
    private Detail[] details;
    private String exit;

    private String hints;
    private IconType iconType;
    private Instruction instruction;
    private ActualEnd maneuverPoint;
    private boolean isRealTimeTransit;
    private long realTimeTransitDelay;
    private SideOfStreet sideOfStreet;
    private String tollZone;
    private String towardsRoadName;
    private String transitTerminus;
    private double travelDistance;
    private long travelDuration;
    private Mode travelMode;
    private String[] signs;
    private Warning[] warnings;

    @JsonProperty("compassDirection")
    public String getCompassDirection() { return compassDirection; }
    @JsonProperty("compassDirection")
    public void setCompassDirection(String value) { this.compassDirection = value; }

    @JsonProperty("details")
    public Detail[] getDetails() { return details; }
    @JsonProperty("details")
    public void setDetails(Detail[] value) { this.details = value; }

    @JsonProperty("exit")
    public String getExit() { return exit; }
    @JsonProperty("exit")
    public void setExit(String value) { this.exit = value; }

    @JsonProperty("iconType")
    public IconType getIconType() { return iconType; }
    @JsonProperty("iconType")
    public void setIconType(IconType value) { this.iconType = value; }

    @JsonProperty("instruction")
    public Instruction getInstruction() { return instruction; }
    @JsonProperty("instruction")
    public void setInstruction(Instruction value) { this.instruction = value; }

    @JsonProperty("isRealTimeTransit")
    public boolean getIsRealTimeTransit() { return isRealTimeTransit; }
    @JsonProperty("isRealTimeTransit")
    public void setIsRealTimeTransit(boolean value) { this.isRealTimeTransit = value; }

    @JsonProperty("maneuverPoint")
    public ActualEnd getManeuverPoint() { return maneuverPoint; }
    @JsonProperty("maneuverPoint")
    public void setManeuverPoint(ActualEnd value) { this.maneuverPoint = value; }

    @JsonProperty("realTimeTransitDelay")
    public long getRealTimeTransitDelay() { return realTimeTransitDelay; }
    @JsonProperty("realTimeTransitDelay")
    public void setRealTimeTransitDelay(long value) { this.realTimeTransitDelay = value; }

    @JsonProperty("sideOfStreet")
    public SideOfStreet getSideOfStreet() { return sideOfStreet; }
    @JsonProperty("sideOfStreet")
    public void setSideOfStreet(SideOfStreet value) { this.sideOfStreet = value; }

    @JsonProperty("tollZone")
    public String getTollZone() { return tollZone; }
    @JsonProperty("tollZone")
    public void setTollZone(String value) { this.tollZone = value; }

    @JsonProperty("towardsRoadName")
    public String getTowardsRoadName() { return towardsRoadName; }
    @JsonProperty("towardsRoadName")
    public void setTowardsRoadName(String value) { this.towardsRoadName = value; }

    @JsonProperty("transitTerminus")
    public String getTransitTerminus() { return transitTerminus; }
    @JsonProperty("transitTerminus")
    public void setTransitTerminus(String value) { this.transitTerminus = value; }

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

    @JsonProperty("signs")
    public String[] getSigns() { return signs; }
    @JsonProperty("signs")
    public void setSigns(String[] value) { this.signs = value; }

    @JsonProperty("warnings")
    public Warning[] getWarnings() { return warnings; }
    @JsonProperty("warnings")
    public void setWarnings(Warning[] value) { this.warnings = value; }
}