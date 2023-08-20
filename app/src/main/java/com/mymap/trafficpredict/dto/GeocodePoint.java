package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodePoint {
    private Type type;
    private double[] coordinates;
    private String calculationMethod;
    private String[] usageTypes;

    @JsonProperty("type")
    public Type getType() { return type; }
    @JsonProperty("type")
    public void setType(Type value) { this.type = value; }

    @JsonProperty("coordinates")
    public double[] getCoordinates() { return coordinates; }
    @JsonProperty("coordinates")
    public void setCoordinates(double[] value) { this.coordinates = value; }

    @JsonProperty("calculationMethod")
    public String getCalculationMethod() { return calculationMethod; }
    @JsonProperty("calculationMethod")
    public void setCalculationMethod(String value) { this.calculationMethod = value; }

    @JsonProperty("usageTypes")
    public String[] getUsageTypes() { return usageTypes; }
    @JsonProperty("usageTypes")
    public void setUsageTypes(String[] value) { this.usageTypes = value; }
}
