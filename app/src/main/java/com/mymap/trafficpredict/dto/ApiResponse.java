package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private String authenticationResultCode;
    private String brandLogoURI;
    private String copyright;
    private ResourceSet[] resourceSets;
    private long statusCode;
    private String statusDescription;
    private String traceID;

    @JsonProperty("authenticationResultCode")
    public String getAuthenticationResultCode() { return authenticationResultCode; }
    @JsonProperty("authenticationResultCode")
    public void setAuthenticationResultCode(String value) { this.authenticationResultCode = value; }

    @JsonProperty("brandLogoUri")
    public String getBrandLogoURI() { return brandLogoURI; }
    @JsonProperty("brandLogoUri")
    public void setBrandLogoURI(String value) { this.brandLogoURI = value; }

    @JsonProperty("copyright")
    public String getCopyright() { return copyright; }
    @JsonProperty("copyright")
    public void setCopyright(String value) { this.copyright = value; }

    @JsonProperty("resourceSets")
    public ResourceSet[] getResourceSets() { return resourceSets; }
    @JsonProperty("resourceSets")
    public void setResourceSets(ResourceSet[] value) { this.resourceSets = value; }

    @JsonProperty("statusCode")
    public long getStatusCode() { return statusCode; }
    @JsonProperty("statusCode")
    public void setStatusCode(long value) { this.statusCode = value; }

    @JsonProperty("statusDescription")
    public String getStatusDescription() { return statusDescription; }
    @JsonProperty("statusDescription")
    public void setStatusDescription(String value) { this.statusDescription = value; }

    @JsonProperty("traceId")
    public String getTraceID() { return traceID; }
    @JsonProperty("traceId")
    public void setTraceID(String value) { this.traceID = value; }
}