package com.mymap.trafficpredict.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.maps.Geopath;
import com.microsoft.maps.Geoposition;
import com.mymap.trafficpredict.BuildConfig;
import com.mymap.trafficpredict.model.Street;
import com.mymap.trafficpredict.model.TrafficManager;
import com.mymap.trafficpredict.service.BingMapsService;

import org.jetbrains.annotations.NotNull;

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

    public MutableLiveData<Geoposition> newCollision = new MutableLiveData<>();

    public MutableLiveData<Geoposition> newDeparturePoint = new MutableLiveData<>(null);
    public MutableLiveData<Geoposition> newArrivalPoint = new MutableLiveData<>(null);


    private ArrayList<String> poolOfDepartureLocations = new ArrayList<>();
    private ArrayList<String> poolOfArrivalLocations = new ArrayList<>();
    private ArrayList<Integer> poolOfColors = new ArrayList<>();

    private TrafficManager trafficManager;

    private static int increasingIndex = 0;

    public MapViewModel() {
        loadRandomDepartureLocations();
        loadRandomArrivalLocations();
        loadPoolOfColors();
        trafficManager = new TrafficManager();
    }

    public void loadRandomDepartureLocations() {
        poolOfDepartureLocations.add("Pianura, Naples, Campania, Italy");
        poolOfDepartureLocations.add("Quarto, Naples, Campania, Italy");
        poolOfDepartureLocations.add("Arenella, Naples, Campania, Italy");
        poolOfDepartureLocations.add("Pozzuoli, Naples, Campania, Italy");
        poolOfDepartureLocations.add("Via Monterusciello, 80078 Pozzuoli Naples");
        poolOfDepartureLocations.add("Soccavo, Naples, Naples, Campania, Italy");
        //poolOfDepartureLocations.add("Campobasso, Campobasso, Molise, Italy");
        //poolOfDepartureLocations.add("Isernia, 86170, Isernia, Molise, Italy");
    }

    public void loadRandomArrivalLocations() {
        //poolOfArrivalLocations.add("Catanzaro, Calabria, Italy");
        //poolOfArrivalLocations.add("Reggio di Calabria, Reggio di Calabria, Calabria, Italy");
        //poolOfArrivalLocations.add("Potenza, Potenza, Basilicata, Italy");
        poolOfArrivalLocations.add("Via Claudio, 80125 Naples Naples");
        poolOfArrivalLocations.add("Fuorigrotta, Naples, Naples, Campania, Italy");
        poolOfArrivalLocations.add("Via Terracina, 80125 Naples Naples");
        poolOfArrivalLocations.add("Via John Fitzgerald Kennedy, 80125 Naples Naples");
        poolOfArrivalLocations.add("Via Pietro Metastasio, 80125 Naples Naples");

    }

    public void loadPoolOfColors() {
        //Blue
        poolOfColors.add(-16776961);
        //Red
        poolOfColors.add(-65536);
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
    // for static targets
    public void calculateRoute() {
        String start = getRandomDepartureLocation();
        String end = getRandomArrivalLocation();
        calculateRoute(start, end);
    }
    // for dynamic targets
    public void calculateRoute(String start, String end) {

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

        Call<JsonNode> call = service.getRoute("Driving", start, end, BuildConfig.CREDENTIALS_KEY, "routePath");

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
                        ArrayList<Geoposition> arrayOfGeopositions = new ArrayList<>();
                        for (JsonNode coordinateNode : coordinatesArray) {
                            if (coordinateNode.isArray() && coordinateNode.size() >= 2) {
                                double latitude = coordinateNode.get(0).asDouble();
                                double longitude = coordinateNode.get(1).asDouble();
                                Geoposition node = new Geoposition(latitude, longitude);
                                arrayOfGeopositions.add(node);
                            } else {
                                System.err.println("Invalid coordinate format: " + coordinateNode.toString());
                            }
                        }
                        //callSnapToRoadApi(newPath);
                        Geopath geopath = new Geopath(arrayOfGeopositions);
                        parseStreetsAndUpdateTraffic(jsonNode, geopath);
                        pathsToMap.setValue(geopath);
                    } else {
                        System.err.println("Coordinates array is missing or not in expected format.");
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    System.err.println("Error processing JSON response.");
                }
            }

            private void parseStreetsAndUpdateTraffic(JsonNode jsonNode, Geopath geopath) {
                try {
                    JsonNode itineraryItemsArray = jsonNode
                            .path("resourceSets")
                            .path(0)
                            .path("resources")
                            .path(0)
                            .path("routeLegs")
                            .path(0)
                            .path("itineraryItems");

                    System.err.println("List of names found..");

                    for (JsonNode itineraryItem : itineraryItemsArray) {
                        JsonNode detailsNode = itineraryItem
                                .path("details")
                                .path(0);

                        if (detailsNode.isMissingNode()) {
                            continue; // Skip this itineraryItem if details are missing
                        }

                        JsonNode namesArray = detailsNode.path("names");
                        if (!namesArray.isMissingNode() && namesArray.isArray() && namesArray.size() > 0) {
                            String firstName = namesArray.get(0).asText();
                            System.err.println("First Name: " + firstName);

                            JsonNode maneuverPointNode = itineraryItem.path("maneuverPoint");

                            if (!maneuverPointNode.isMissingNode()) {
                                double latitude = maneuverPointNode.path("coordinates").get(0).asDouble();
                                double longitude = maneuverPointNode.path("coordinates").get(1).asDouble();
                                System.err.println("Coordinates: Latitude " + latitude + ", Longitude " + longitude);
                                Geoposition streetGeoposition = new Geoposition(latitude, longitude);
                                boolean thereIsCollision = trafficManager.putAndCheckCollision(new Street(firstName, streetGeoposition), geopath);
                                if (thereIsCollision) {
                                    newCollision.setValue(streetGeoposition);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Get more precise points, but too slow for longer paths
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
                                System.err.println("Snap to road API error after response received..");
                                // Handle API error
                                return null;
                            }
                        } catch (IOException e) {
                            System.err.println("Snap to road API failed..");
                            throw new RuntimeException(e);
                        }
                    }
                };
            }

            @Override
            public void onFailure(Call<JsonNode> call, Throwable t) {
                // Handle API call failure
                System.err.println("Api failure somewhere.." + t.getMessage());
            }
        });
    }
}
