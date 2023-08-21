package com.mymap.trafficpredict.service;

import com.fasterxml.jackson.databind.JsonNode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BingMapsService {
    @GET("Routes/{travelMode}")
    Call<JsonNode> getRoute(
            @Path("travelMode") String travelMode,
            @Query("waypoint.1") String start,
            @Query("waypoint.2") String end,
            @Query("key") String apiKey,
            @Query("ra") String routeAttributes
    );
}

