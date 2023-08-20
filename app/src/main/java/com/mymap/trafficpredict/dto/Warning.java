package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Warning {
    private TrafficCongestion severity;
    private String text;
    private WarningType warningType;
    private String origin;
    private String to;
    private Time endTime;
    private Time startTime;

    @JsonProperty("severity")
    public TrafficCongestion getSeverity() { return severity; }
    @JsonProperty("severity")
    public void setSeverity(TrafficCongestion value) { this.severity = value; }

    @JsonProperty("text")
    public String getText() { return text; }
    @JsonProperty("text")
    public void setText(String value) { this.text = value; }

    @JsonProperty("warningType")
    public WarningType getWarningType() { return warningType; }
    @JsonProperty("warningType")
    public void setWarningType(WarningType value) { this.warningType = value; }

    @JsonProperty("origin")
    public String getOrigin() { return origin; }
    @JsonProperty("origin")
    public void setOrigin(String value) { this.origin = value; }

    @JsonProperty("to")
    public String getTo() { return to; }
    @JsonProperty("to")
    public void setTo(String value) { this.to = value; }

    @JsonProperty("endTime")
    public Time getEndTime() { return endTime; }
    @JsonProperty("endTime")
    public void setEndTime(Time value) { this.endTime = value; }

    @JsonProperty("startTime")
    public Time getStartTime() { return startTime; }
    @JsonProperty("startTime")
    public void setStartTime(Time value) { this.startTime = value; }
}