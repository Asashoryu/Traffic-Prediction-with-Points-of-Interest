package com.mymap.trafficpredict.model;

import com.microsoft.maps.Geopath;
import com.microsoft.maps.Geoposition;

import java.util.ArrayList;
import java.util.HashMap;

public class TrafficManager {
    private HashMap<Street, ArrayList<Geopath>> associativeTable = new HashMap<>();

    public TrafficManager() {}

    public HashMap<Street, ArrayList<Geopath>> getAssociativeTable() {
        return associativeTable;
    }

    public void setAssociativeTable(HashMap<Street, ArrayList<Geopath>> associativeTable) {
        this.associativeTable = associativeTable;
    }

    public boolean putAndCheckCollision(Street street, Geopath geopath) {
        ArrayList<Geopath> arrayOfGeopaths = associativeTable.get(street);
        if (arrayOfGeopaths != null) {
            if (!arrayOfGeopaths.contains(geopath)) {
                arrayOfGeopaths.add(geopath);
                return arrayOfGeopaths.size() > 1;
            }
        }
        else {
            arrayOfGeopaths = new ArrayList<>();
            arrayOfGeopaths.add(geopath);
            associativeTable.put(street, arrayOfGeopaths);
        }
        return false;
    }
}
