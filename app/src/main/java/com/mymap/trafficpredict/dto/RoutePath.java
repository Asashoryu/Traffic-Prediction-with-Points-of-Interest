package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoutePath {
    private Object[] generalizations;
    private Line line;

    @JsonProperty("generalizations")
    public Object[] getGeneralizations() { return generalizations; }
    @JsonProperty("generalizations")
    public void setGeneralizations(Object[] value) { this.generalizations = value; }

    @JsonProperty("line")
    public Line getLine() { return line; }
    @JsonProperty("line")
    public void setLine(Line value) { this.line = value; }
}
