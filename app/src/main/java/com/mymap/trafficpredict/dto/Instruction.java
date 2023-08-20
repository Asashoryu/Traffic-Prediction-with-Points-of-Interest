package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Instruction {
    private Object formattedText;
    private String maneuverType;
    private String text;

    @JsonProperty("formattedText")
    public Object getFormattedText() { return formattedText; }
    @JsonProperty("formattedText")
    public void setFormattedText(Object value) { this.formattedText = value; }

    @JsonProperty("maneuverType")
    public String getManeuverType() { return maneuverType; }
    @JsonProperty("maneuverType")
    public void setManeuverType(String value) { this.maneuverType = value; }

    @JsonProperty("text")
    public String getText() { return text; }
    @JsonProperty("text")
    public void setText(String value) { this.text = value; }
}
