package com.mymap.trafficpredict.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.microsoft.maps.Geopath;
import com.microsoft.maps.Geoposition;
import com.mymap.trafficpredict.BuildConfig;
import com.mymap.trafficpredict.dto.ApiResponse;
import com.mymap.trafficpredict.dto.ItineraryItem;
import com.mymap.trafficpredict.dto.Mode;
import com.mymap.trafficpredict.service.BingMapsService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MapViewModel extends ViewModel {

    public MutableLiveData<Geopath> pathsToMap = new MutableLiveData<>();

    public ArrayList<String> poolOfDepartureLocations = new ArrayList<>();
    public ArrayList<String> poolOfArrivalLocations = new ArrayList<>();
    public ArrayList<Integer> poolOfColors = new ArrayList<>();

    private static int increasingIndex = 0;

    public MapViewModel() {
        loadRandomDepartureLocations();
        loadRandomArrivalLocations();
        loadPoolOfColors();
    }

    public void loadRandomDepartureLocations() {
        poolOfDepartureLocations.add("Pianura, Naples, Campania, Italy");
        poolOfDepartureLocations.add("Quarto, Naples, Campania, Italy");
        poolOfDepartureLocations.add("Agnano");
        poolOfDepartureLocations.add("Campobasso, Campobasso, Molise, Italy");
        poolOfDepartureLocations.add("Isernia, 86170, Isernia, Molise, Italy");
    }

    public void loadRandomArrivalLocations() {
        poolOfArrivalLocations.add("Catanzaro, Calabria, Italy");
        poolOfArrivalLocations.add("Reggio di Calabria, Reggio di Calabria, Calabria, Italy");
        poolOfArrivalLocations.add("Potenza, Potenza, Basilicata, Italy");
    }

    public void loadPoolOfColors() {
        //Red
        poolOfColors.add(-65536);
        //Purple
        poolOfColors.add(-65281);
        //Yellow
        poolOfColors.add(-256);
        //Green
        poolOfColors.add(-16711936);
        //Blue
        poolOfColors.add(-16776961);
        //LightBlue
        poolOfColors.add(-16711681);
        //Orange
        poolOfColors.add(-32768);
    }

    public String getRandomDepartureLocation() {
        int randomInt = generateRandomNonNegativeInt();
        int maxIndexOfDepartureLocations = poolOfDepartureLocations.size() - 1;
        return poolOfDepartureLocations.get(randomInt%maxIndexOfDepartureLocations);
    }

    public String getRandomArrivalLocation() {
        int randomInt = generateRandomNonNegativeInt();
        int maxIndexOfArrivalLocations = poolOfArrivalLocations.size() - 1;
        return poolOfArrivalLocations.get(randomInt%maxIndexOfArrivalLocations);
    }

    public int getColorInOrder() {
        int color = poolOfColors.get(increasingIndex%poolOfColors.size());
        increasingIndex++;
        return color;
    }

    private int generateRandomNonNegativeInt() {
        Random random = new Random();
        int randomNonNegativeInt = random.nextInt(Integer.MAX_VALUE);
        System.out.println("Random Non-Negative int generated: " + randomNonNegativeInt);
        return randomNonNegativeInt;
    }

    public void calculateRoute() {
        String start = getRandomDepartureLocation();
        String end = getRandomArrivalLocation();
        getRoute(start, end);
    }

    private void getRoute(String start, String end) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // Add an interceptor to the OkHttp client
        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                // Get the request
                Request request = chain.request();

                // Get the URL as a string and print it
                String url = request.url().toString();
                System.out.println("Request URL: " + url);

                // Proceed with the request
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dev.virtualearth.net/REST/v1/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();

        BingMapsService service = retrofit.create(BingMapsService.class);

        Call<ApiResponse> call = service.getRoute("Driving", start, end, BuildConfig.CREDENTIALS_KEY, "all");

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                // Convert the response body to a JSON string
                Gson gson = new Gson();
                JsonElement responseJson = gson.toJsonTree(response.body());

                // Print the JSON string
                String jsonString = gson.toJson(responseJson);
                System.out.println(jsonString);

                // Parse the response and extract route details
                ApiResponse apiResponse = response.body();

                Mode m = apiResponse.getResourceSets()[0].getResources()[0].getTravelMode();

                if (apiResponse != null && m != null) {
                    buildAndShowPath(apiResponse);
                } else {
                    // Handle case where response doesn't have routes or is null
                    System.err.println("Api response received but with some problems in formatting..");
                }
            }

            private void buildAndShowPath(ApiResponse apiResponse) {
                ArrayList<Geoposition> newPath = new ArrayList();
                double[] startCoordinates = apiResponse.getResourceSets()[0].getResources()[0].getRouteLegs()[0].getActualStart().getCoordinates();
                double[] endCoordinates = apiResponse.getResourceSets()[0].getResources()[0].getRouteLegs()[0].getActualEnd().getCoordinates();
                newPath.add(new Geoposition(startCoordinates[0], startCoordinates[1]));
                ItineraryItem[] items = apiResponse.getResourceSets()[0].getResources()[0].getRouteLegs()[0].getItineraryItems();
                for (ItineraryItem i : items) {
                    Geoposition node = new Geoposition(i.getManeuverPoint().getCoordinates()[0], i.getManeuverPoint().getCoordinates()[1]);
                    newPath.add(node);
                    //System.err.println(i.getDetails()[0].getNames()[0]);
                }
                newPath.add(new Geoposition(endCoordinates[0], endCoordinates[1]));
                //Comment to not snap
                //callSnapToRoadApi(newPath);
                pathsToMap.setValue(new Geopath(newPath));
            }

            private void callSnapToRoadApi(ArrayList<Geoposition> newPath) {
                // Construct the API request URL
                String apiKey = BuildConfig.CREDENTIALS_KEY;
                String endpoint = "https://dev.virtualearth.net/REST/v1/Routes/SnapToRoad";
                StringBuilder waypoints = new StringBuilder();
                for (int i = 0; i < newPath.size(); i++) {
                    Geoposition g = newPath.get(i);
                    waypoints.append(g.getLatitude()).append(",").append(g.getLongitude());
                    if (i < newPath.size() - 1) {
                        waypoints.append(";");
                    }
                }

                String url = endpoint + "?points=" + waypoints.toString() + "&interpolate=true&key=" + apiKey;

                System.err.println("Snap to road url: " + url);

                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        try {
                            okhttp3.Response response = client.newCall(request).execute();
                            if (response.isSuccessful()) {
                                return response.body().string();
                            } else {
                                // Handle API error
                                return null;
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    protected void onPostExecute(String responseBody) {
                        super.onPostExecute(responseBody);
                        if (responseBody != null) {
                            // Parse the responseBody JSON to extract snapped points
                            Geopath snappedPath = parseSnappedPath(responseBody);
                            System.err.println(responseBody);
                            // Update your path representation with the snapped points
                            pathsToMap.setValue(snappedPath);
                        }
                    }
                }.execute();
            }

            private Geopath parseSnappedPath(String responseBody) {
                try {
                    JSONObject jsonResponse = new JSONObject(responseBody);

                    System.err.println(jsonResponse);

                    JSONArray resourceSets = jsonResponse.getJSONArray("resourceSets");
                    JSONObject firstResourceSet = resourceSets.getJSONObject(0);

                    JSONArray resources = firstResourceSet.getJSONArray("resources");
                    JSONObject firstResource = resources.getJSONObject(0);

                    JSONArray snappedPoints = firstResource.getJSONArray("snappedPoints");

                    ArrayList<Geoposition> snappedPath = new ArrayList<>();
                    for (int i = 0; i < snappedPoints.length(); i++) {
                        JSONObject snappedPoint = snappedPoints.getJSONObject(i);
                        JSONObject coordinate = snappedPoint.getJSONObject("coordinate");
                        double latitude = coordinate.getDouble("latitude");
                        double longitude = coordinate.getDouble("longitude");

                        snappedPath.add(new Geoposition(latitude, longitude));
                    }

                    return new Geopath(snappedPath);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null; // Handle the error case appropriately
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handle API call failure
                System.err.println("Api failure somewhere.." + t.getMessage());
            }
        });
    }
}
