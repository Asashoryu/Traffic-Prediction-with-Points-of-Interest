package com.mymap.trafficpredict.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndLocationAddress {
    private String addressLine;
    private String adminDistrict;
    private String adminDistrict2;
    private String countryRegion;
    private String formattedAddress;
    private String locality;
    private String postalCode;

    @JsonProperty("addressLine")
    public String getAddressLine() { return addressLine; }
    @JsonProperty("addressLine")
    public void setAddressLine(String value) { this.addressLine = value; }

    @JsonProperty("adminDistrict")
    public String getAdminDistrict() { return adminDistrict; }
    @JsonProperty("adminDistrict")
    public void setAdminDistrict(String value) { this.adminDistrict = value; }

    @JsonProperty("adminDistrict2")
    public String getAdminDistrict2() { return adminDistrict2; }
    @JsonProperty("adminDistrict2")
    public void setAdminDistrict2(String value) { this.adminDistrict2 = value; }

    @JsonProperty("countryRegion")
    public String getCountryRegion() { return countryRegion; }
    @JsonProperty("countryRegion")
    public void setCountryRegion(String value) { this.countryRegion = value; }

    @JsonProperty("formattedAddress")
    public String getFormattedAddress() { return formattedAddress; }
    @JsonProperty("formattedAddress")
    public void setFormattedAddress(String value) { this.formattedAddress = value; }

    @JsonProperty("locality")
    public String getLocality() { return locality; }
    @JsonProperty("locality")
    public void setLocality(String value) { this.locality = value; }

    @JsonProperty("postalCode")
    public String getPostalCode() { return postalCode; }
    @JsonProperty("postalCode")
    public void setPostalCode(String value) { this.postalCode = value; }
}