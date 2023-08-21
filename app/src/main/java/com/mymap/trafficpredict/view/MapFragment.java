package com.mymap.trafficpredict.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.model.Polyline;
import com.microsoft.maps.*;
import com.microsoft.maps.search.MapLocation;
import com.mymap.trafficpredict.BuildConfig;
import com.mymap.trafficpredict.R;
import com.mymap.trafficpredict.databinding.FragmentMapBinding;
import com.mymap.trafficpredict.viewmodel.MapViewModel;

import com.microsoft.maps.MapRenderMode;
import com.microsoft.maps.MapView;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;
    private View fragmentView;
    private FragmentMapBinding mapBinding;

    private static boolean centered = false;

    private MapView mMapView;

    MapElementLayer linesLayer;

    Thread twinklingThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapBinding = FragmentMapBinding.inflate(inflater, container, false);

        fragmentView = mapBinding.getRoot();
        startMap(savedInstanceState);


        observeRoutesUpdates();

        return fragmentView;
    }

    public void startMap(Bundle savedInstanceState) {
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        mapBinding.setMapViewModel(mapViewModel);

        mMapView = new MapView(getContext(), MapRenderMode.VECTOR);  // or use MapRenderMode.RASTER for 2D map
        mMapView.setCredentialsKey(BuildConfig.CREDENTIALS_KEY);
        mapBinding.mapView.addView(mMapView);
        // add polylines layer
        startPolylinesLayer();
        // center the map on the first rendering
        mMapView.addOnMapLoadingStatusChangedListener((status) -> {
            if (status == MapLoadingStatus.COMPLETE && !centered) {
                centerOnNaples();
                centered = true;
            }
            return false;
        });
        mMapView.onCreate(savedInstanceState);
    }

    private void startPolylinesLayer() {
        linesLayer = new MapElementLayer();
        mMapView.getLayers().add(linesLayer);
        beginPeriodicalTwinkleOfPaths();
    }

    public void observeRoutesUpdates() {
        mapViewModel.pathsToMap.observe(getViewLifecycleOwner(), (newPath) -> {
            System.out.println("Observed successfully..");
            displayPath(newPath);
        });
    }

    public void displayPath(Geopath path) {
            MapPolyline mapPolyline = new MapPolyline();
            mapPolyline.setPath(path);
            mapPolyline.setStrokeColor(mapViewModel.getColorInOrder());

            mapPolyline.setStrokeWidth(3);
            linesLayer.getElements().add(mapPolyline);
    }

    public void beginPeriodicalTwinkleOfPaths() {
        Runnable r = () -> {
            while(!Thread.currentThread().isInterrupted()) {
                MapElement prevE = null;
                Iterator<MapElement> i = linesLayer.getElements().iterator();
                while (i.hasNext()) {
                    try {
                        MapElement e = i.next();
                        Thread.currentThread().sleep(1000);
                        e.setZIndex(3);
                        if (prevE != null) {
                            e.setZIndex(2);
                        }
                        prevE = e;
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };
        twinklingThread = new Thread(r);
        twinklingThread.start();
    }

    public void centerOnNaples() {
        // Define the desired location coordinates
        double naplesLatitude = 40.85631;
        double naplesLongitude = 14.24641;

        // Create a MapScene centered on the target location and set the desired zoom level
        MapScene mapScene = MapScene.createFromLocationAndZoomLevel(new Geopoint(naplesLatitude, naplesLongitude), 8); // Adjust zoom level as needed

        // Set the MapScene to move the map to the specified location
        mMapView.setScene(mapScene, MapAnimationKind.BOW);
    }
}