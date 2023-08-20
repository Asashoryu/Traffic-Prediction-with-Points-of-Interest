package com.mymap.trafficpredict.model;

public class Node {
    private double latitude;
    private double longitude;
    private double altitude = 0.0;

    public Node(double lat, double lon) {
        latitude = lat;
        longitude = lon;
    }

    @Override
    public String toString() {
        return "[" + latitude +", " + longitude + "]";
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}
