package com.mymap.trafficpredict.model;

import com.microsoft.maps.Geoposition;

public class Street {
    private String name;
    private Geoposition position;

    public Street(String name, Geoposition position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Street)) return false;
        Street s = (Street) o;
        return name.equals(s.name);
    }
}
