package com.mymap.trafficpredict.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.microsoft.maps.*;
import com.mymap.trafficpredict.BuildConfig;
import com.mymap.trafficpredict.databinding.FragmentMapBinding;
import com.mymap.trafficpredict.viewmodel.MapViewModel;

import com.microsoft.maps.MapRenderMode;
import com.microsoft.maps.MapView;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;
    private View fragmentView;
    private FragmentMapBinding mapBinding;

    private static boolean centered = false;

    private MapView mMapView;

    MapElementLayer linesLayer;

    Thread twinklingThread;

    LinkedBlockingQueue<MapElement> bq = new LinkedBlockingQueue();

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
        startPeriodicalTwinkleOfPaths();
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
        try {
            bq.put(mapPolyline);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void startPeriodicalTwinkleOfPaths() {
        Runnable r = () -> {
            MapElement prevE = null;
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.currentThread().sleep(1000);
                    MapElement e = bq.take();
                    System.out.println("Twinkle try..1");
                    e.setZIndex(3);
                    if (prevE != null) {
                        prevE.setZIndex(2);
                    }
                    prevE = e;
                    bq.put(e);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
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