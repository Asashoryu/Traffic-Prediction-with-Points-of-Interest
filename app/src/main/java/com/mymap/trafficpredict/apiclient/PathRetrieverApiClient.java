package com.mymap.trafficpredict.apiclient;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PathRetrieverApiClient {
    private static final String BASE_URL = "https://dev.virtualearth.net/REST/v1/Routes/";

    public interface OnRouteResponseListener {
        void onSuccess(String response);
        void onFailure(Exception e);
    }

    public static void getRoute(String travelMode, String start, String end, String apiKey, OnRouteResponseListener listener) {
        String apiUrl = BASE_URL + travelMode + "?waypoint.1=" + start + "&waypoint.2=" + end + "&key=" + apiKey;

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        return response.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String response) {
                if (response != null) {
                    listener.onSuccess(response);
                } else {
                    listener.onFailure(new Exception("API call failed"));
                }
            }
        }.execute(apiUrl);
    }
}
