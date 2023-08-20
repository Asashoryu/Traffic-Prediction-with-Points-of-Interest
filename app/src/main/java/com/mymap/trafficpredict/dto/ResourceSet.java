package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceSet {
    private long estimatedTotal;
    private Resource[] resources;

    @JsonProperty("estimatedTotal")
    public long getEstimatedTotal() { return estimatedTotal; }
    @JsonProperty("estimatedTotal")
    public void setEstimatedTotal(long value) { this.estimatedTotal = value; }

    @JsonProperty("resources")
    public Resource[] getResources() { return resources; }
    @JsonProperty("resources")
    public void setResources(Resource[] value) { this.resources = value; }
}
