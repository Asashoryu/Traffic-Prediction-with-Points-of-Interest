package com.mymap.trafficpredict.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.maps.Geopath;
import com.microsoft.maps.Geoposition;
import com.mymap.trafficpredict.BuildConfig;
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
import retrofit2.internal.EverythingIsNonNull;

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
        poolOfDepartureLocations.add("Arenella, Naples, Campania, Italy");
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
        //Blue
        poolOfColors.add(-16776961);
        //Green
        poolOfColors.add(-16711936);
        //Yellow
        poolOfColors.add(-256);
        //Purple
        poolOfColors.add(-65281);
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

        Call<JsonNode> call = service.getRoute("Driving", start, end, BuildConfig.CREDENTIALS_KEY, "all");

        call.enqueue(new Callback<JsonNode>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<JsonNode> call, Response<JsonNode> response) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode;
                try {
                    jsonNode = objectMapper.readTree(String.valueOf(response.body()));
                    System.out.println("Json response message is: " + jsonNode.toString());

                    // Extract specific fields from JSON
                    JsonNode coordinatesArray = jsonNode
                            .path("resourceSets")
                            .path(0)
                            .path("resources")
                            .path(0)
                            .path("routePath")
                            .path("line")
                            .path("coordinates");

                    if (coordinatesArray.isArray()) {
                        ArrayList<Geoposition> newPath = new ArrayList<>();
                        for (JsonNode coordinateNode : coordinatesArray) {
                            if (coordinateNode.isArray() && coordinateNode.size() >= 2) {
                                double latitude = coordinateNode.get(0).asDouble();
                                double longitude = coordinateNode.get(1).asDouble();
                                Geoposition node = new Geoposition(latitude, longitude);
                                newPath.add(node);
                            } else {
                                System.err.println("Invalid coordinate format: " + coordinateNode.toString());
                            }
                        }
                        pathsToMap.setValue(new Geopath(newPath));
                    } else {
                        System.err.println("Coordinates array is missing or not in expected format.");
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    System.err.println("Error processing JSON response.");
                }
            }


            private void buildAndShowPath(JsonNode resourceNode) {
                ArrayList<Geoposition> newPath = new ArrayList<>();

                JsonNode routePath = resourceNode.path("routePath");
                JsonNode line = routePath.path("line");
                JsonNode coordinates = line.path("coordinates");

                if (!coordinates.isNull() && coordinates.isArray()) {
                    for (JsonNode coordinate : coordinates) {
                        if (coordinate.isArray() && coordinate.size() >= 2) {
                            double latitude = coordinate.get(0).asDouble();
                            double longitude = coordinate.get(1).asDouble();
                            Geoposition node = new Geoposition(latitude, longitude);
                            newPath.add(node);
                        } else {
                            System.err.println("Invalid coordinate format: " + coordinate.toString());
                        }
                    }
                    pathsToMap.setValue(new Geopath(newPath));
                } else {
                    System.err.println("Coordinates are missing or not in expected format.");
                }
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
            public void onFailure(Call<JsonNode> call, Throwable t) {
                // Handle API call failure
                System.err.println("Api failure somewhere.." + t.getMessage());
            }
        });
    }
}
