package com.mymap.trafficpredict.service;

import com.mymap.trafficpredict.dto.ApiResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BingMapsService {
    @GET("Routes/{travelMode}")
    Call<ApiResponse> getRoute(
            @Path("travelMode") String travelMode,
            @Query("waypoint.1") String start,
            @Query("waypoint.2") String end,
            @Query("key") String apiKey,
            @Query("ra") String routeAttributes
    );
}

