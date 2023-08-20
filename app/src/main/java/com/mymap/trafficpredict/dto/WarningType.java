package com.mymap.trafficpredict.dto;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public enum WarningType {
    ACCIDENT, ADMIN_DIVISION_CHANGE, BLOCKED_ROAD, CHECK_TIMETABLE,
    CONGESTION, COUNTRY_CHANGE, DISABLE_VEHICLE, GATE_ACCESS, GET_OFF_TRANSIT,
    GET_ON_TRANSIT, ILLEGAL_U_TURN, MASS_TRANSIT, MISCELLANEUOUS, NO_INCIDENT,
    NONE, OTHER, OTHER_NEWS, OTHER_TRAFFIC_INCIDENTS, PLANNED_EVENTS,
    PRIVATE_ROAD, RESTRICTED_TURN, ROAD_CLOSURES, ROAD_HAZARD, SCHEDULED_CONSTRUCTION,
    SEASONAL_CLOSURES, TOLLBOOTH, TOLL_ROAD, TOLL_ZONE_ENTER, TOLL_ZONE_EXIT,
    TRAFFIC_FLOW, TRANSIT_LINE_CHANGE, UNPAVED_ROAD, WEATHER;

    @JsonValue
    public String toValue() {
        switch (this) {
            case ACCIDENT: return "Accident";
            case ADMIN_DIVISION_CHANGE: return "AdminDivisionChange";
            case BLOCKED_ROAD: return "BlockedRoad";
            case CHECK_TIMETABLE: return "CheckTimetable";
            case CONGESTION: return "Congestion";
            case COUNTRY_CHANGE: return "CountryChange";
            case DISABLE_VEHICLE: return "DisableVehicle";
            case GATE_ACCESS: return "GateAccess";
            case GET_OFF_TRANSIT: return "GetOffTransit";
            case GET_ON_TRANSIT: return "GetOnTransit";
            case ILLEGAL_U_TURN: return "IllegalUTurn";
            case MASS_TRANSIT: return "MassTransit";
            case MISCELLANEUOUS: return "Miscellaneous";
            case NO_INCIDENT: return "NoIncident";
            case NONE: return "None";
            case OTHER: return "Other";
            case OTHER_NEWS: return "OtherNews";
            case OTHER_TRAFFIC_INCIDENTS: return "OtherTrafficIncidents";
            case PLANNED_EVENTS: return "PlannedEvents";
            case PRIVATE_ROAD: return "PrivateRoad";
            case RESTRICTED_TURN: return "RestrictedTurn";
            case ROAD_CLOSURES: return "RoadClosures";
            case ROAD_HAZARD: return "RoadHazard";
            case SCHEDULED_CONSTRUCTION: return "ScheduledConstruction";
            case SEASONAL_CLOSURES: return "SeasonalClosures";
            case TOLLBOOTH: return "Tollbooth";
            case TOLL_ROAD: return "TollRoad";
            case TOLL_ZONE_ENTER: return "TollZoneEnter";
            case TOLL_ZONE_EXIT: return "TollZoneExit";
            case TRAFFIC_FLOW: return "TrafficFlow";
            case TRANSIT_LINE_CHANGE: return "TransitLineChange";
            case UNPAVED_ROAD: return "UnpavedRoad";
            case WEATHER: return "Weather";
        }
        return null;
    }

    @JsonCreator
    public static WarningType forValue(String value) throws IOException {
        if (value.equals("Accident")) return ACCIDENT;
        if (value.equals("AdminDivisionChange")) return ADMIN_DIVISION_CHANGE;
        if (value.equals("BlockedRoad")) return BLOCKED_ROAD;
        if (value.equals("CheckTimetable")) return CHECK_TIMETABLE;
        if (value.equals("Congestion")) return CONGESTION;
        if (value.equals("CountryChange")) return COUNTRY_CHANGE;
        if (value.equals("DisableVehicle")) return DISABLE_VEHICLE;
        if (value.equals("GateAccess")) return GATE_ACCESS;
        if (value.equals("GetOffTransit")) return GET_OFF_TRANSIT;
        if (value.equals("GetOnTransit")) return GET_ON_TRANSIT;
        if (value.equals("IllegalUTurn")) return ILLEGAL_U_TURN;
        if (value.equals("MassTransit")) return MASS_TRANSIT;
        if (value.equals("Miscellaneous")) return MISCELLANEUOUS;
        if (value.equals("NoIncident")) return NO_INCIDENT;
        if (value.equals("None")) return NONE;
        if (value.equals("Other")) return OTHER;
        if (value.equals("OtherNews")) return OTHER_NEWS;
        if (value.equals("OtherTrafficIncidents")) return OTHER_TRAFFIC_INCIDENTS;
        if (value.equals("PlannedEvents")) return PLANNED_EVENTS;
        if (value.equals("PrivateRoad")) return PRIVATE_ROAD;
        if (value.equals("RestrictedTurn")) return RESTRICTED_TURN;
        if (value.equals("RoadClosures")) return ROAD_CLOSURES;
        if (value.equals("RoadHazard")) return ROAD_HAZARD;
        if (value.equals("ScheduledConstruction")) return SCHEDULED_CONSTRUCTION;
        if (value.equals("SeasonalClosures")) return SEASONAL_CLOSURES;
        if (value.equals("Tollbooth")) return TOLLBOOTH;
        if (value.equals("TollRoad")) return TOLL_ROAD;
        if (value.equals("TollZoneEnter")) return TOLL_ZONE_ENTER;
        if (value.equals("TollZoneExit")) return TOLL_ZONE_EXIT;
        if (value.equals("TrafficFlow")) return TRAFFIC_FLOW;
        if (value.equals("TransitLineChange")) return TRANSIT_LINE_CHANGE;
        if (value.equals("UnpavedRoad")) return UNPAVED_ROAD;
        if (value.equals("Weather")) return WEATHER;
        throw new IOException("Cannot deserialize WarningType");
    }
}