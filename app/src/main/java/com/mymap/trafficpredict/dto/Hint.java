package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hint {
    private String hintType;
    private String text;

    @JsonProperty("hintType")
    public String getHintType() { return hintType; }
    @JsonProperty("hintType")
    public void setHintType(String value) { this.hintType = value; }

    @JsonProperty("text")
    public String getText() { return text; }
    @JsonProperty("text")
    public void setText(String value) { this.text = value; }
}
