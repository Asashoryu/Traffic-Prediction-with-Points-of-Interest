package com.mymap.trafficpredict.view;

import android.graphics.BitmapFactory;
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

    private MapElementLayer linesLayer;

    private MapElementLayer pinLayer;

    private Thread twinklingThread;

    private LinkedBlockingQueue<MapElement> bq = new LinkedBlockingQueue();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapBinding = FragmentMapBinding.inflate(inflater, container, false);

        fragmentView = mapBinding.getRoot();
        startMap(savedInstanceState);

        //observePinsReady();
        observeRoutesUpdates();
        observeTrafficCollisionUpdates();

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
        startPinsLayer();
        // center the map on the first rendering
        mMapView.addOnMapLoadingStatusChangedListener((status) -> {
            if (status == MapLoadingStatus.COMPLETE && !centered) {
                centerOnNaples();
                centered = true;
            }
            return false;
        });
        mMapView.addOnMapHoldingListener((doubleTapEvent) -> {
            if (mapViewModel.newDeparturePoint.getValue() == null) {
                mapViewModel.newDeparturePoint.setValue(doubleTapEvent.location.getPosition());
            }
            else {
                Geoposition departureGeoposition = mapViewModel.newDeparturePoint.getValue();
                Geoposition arrivalGeoposition = doubleTapEvent.location.getPosition();
                String departure = departureGeoposition.getLatitude() + "," + departureGeoposition.getLongitude();
                String arrival = arrivalGeoposition.getLatitude() + "," + arrivalGeoposition.getLongitude();
                mapViewModel.calculateRoute(departure, arrival);
                mapViewModel.newDeparturePoint.setValue(null);
            }
            return true;
        });
        mMapView.onCreate(savedInstanceState);
    }

    private void startPolylinesLayer() {
        linesLayer = new MapElementLayer();
        mMapView.getLayers().add(linesLayer);
        startPeriodicalTwinkleOfPaths();
    }

    private void startPinsLayer() {
        pinLayer = new MapElementLayer();
        mMapView.getLayers().add(pinLayer);
    }

    public void observeRoutesUpdates() {
        mapViewModel.pathsToMap.observe(getViewLifecycleOwner(), (newPath) -> {
            System.err.println("New path observed successfully..");
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

    public void observeTrafficCollisionUpdates() {
        mapViewModel.newCollision.observe(getViewLifecycleOwner(), (geoposition) -> {
            System.err.println("New collision observed successfully..");
            displayTrafficCollision(geoposition);
        });
    }

    public void displayTrafficCollision(Geoposition geoposition) {
        displayPinpoint(geoposition);
    }

    public void displayPinpoint(Geoposition geoposition) {
        MapIcon pushpin = new MapIcon();
        pushpin.setLocation(new Geopoint(geoposition));
        pushpin.setTitle("Here collision expected");

        MapFlyout flyout = new MapFlyout();
        flyout.setTitle("Traffic");
        flyout.setDescription("Collision expected");
        pushpin.setFlyout(flyout);

        pinLayer.getElements().add(pushpin);

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
        MapScene mapScene = MapScene.createFromLocationAndZoomLevel(new Geopoint(naplesLatitude, naplesLongitude), 12); // Adjust zoom level as needed

        // Set the MapScene to move the map to the specified location
        mMapView.setScene(mapScene, MapAnimationKind.BOW);
    }
}