package com.mymap.trafficpredict.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.microsoft.maps.*;
import com.mymap.trafficpredict.BuildConfig;
import com.mymap.trafficpredict.R;
import com.mymap.trafficpredict.databinding.FragmentMapBinding;
import com.mymap.trafficpredict.viewmodel.MapViewModel;

import com.microsoft.maps.MapRenderMode;
import com.microsoft.maps.MapView;

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

        // display traffic on the map
        TrafficFlowMapLayer tfmpl = new TrafficFlowMapLayer();
        mMapView.getLayers().add(tfmpl);

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
        mMapView.addOnMapHoldingListener((longTapEvent) -> {
            System.err.println("This is the location long-tapped: " + longTapEvent.location.getPosition() + ", " + longTapEvent.position.y);
            if (mapViewModel.newDeparturePoint.getValue() == null) {
                // if it's the first point, then it's a departure point
                mapViewModel.newDeparturePoint.setValue(longTapEvent.location.getPosition());
                displayDeparturePinpoint(longTapEvent.location.getPosition());
            }
            else {
                // if it's not the first point, then it's the arrival point
                displayArrivalPinpoint(longTapEvent.location.getPosition());
                Geoposition departureGeoposition = mapViewModel.newDeparturePoint.getValue();
                Geoposition arrivalGeoposition = longTapEvent.location.getPosition();
                String departure = departureGeoposition.getLatitude() + "," + departureGeoposition.getLongitude();
                String arrival = arrivalGeoposition.getLatitude() + "," + arrivalGeoposition.getLongitude();
                mapViewModel.calculateRoute(departure, arrival);
                // correctDepartureAndArrivalPinsOnMap(mapViewModel.newDeparturePoint.getValue(), mapViewModel.newArrivalPoint.getValue());
                mapViewModel.newDeparturePoint.setValue(null);
                mapViewModel.newArrivalPoint.setValue(null);
            }
            return true;
        });
        mMapView.onCreate(savedInstanceState);
    }
    // method for correcting departure and arrival points according to the accual path provided
    public void correctDepartureAndArrivalPinsOnMap(Geoposition departure, Geoposition arrival) {
        int numberOfPins = pinLayer.getElements().size();
        // remove arrival pin
        pinLayer.getElements().remove(numberOfPins - 1);
        // remove departure pin
        pinLayer.getElements().remove(numberOfPins - 2);
        // update departure pin
        displayDeparturePinpoint(departure);
        // update arrival pin
        displayArrivalPinpoint(arrival);
    }

    private void startPolylinesLayer() {
        linesLayer = new MapElementLayer();
        mMapView.getLayers().add(linesLayer);
        // Uncomment to add the twinkling effect in order to distinguish overlapping paths
        // startPeriodicalTwinkleOfPaths();
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
            System.err.println("Here a collision point: " + geoposition.getLatitude() + ", " + geoposition.getLongitude());
            displayTrafficCollision(geoposition);
        });
    }

    public void displayTrafficCollision(Geoposition geoposition) {
        displayTrafficCollisionPinpoint(geoposition);
    }

    public void displayTrafficCollisionPinpoint(Geoposition geoposition) {
        MapIcon pushpin = new MapIcon();
        pushpin.setLocation(new Geopoint(geoposition));
        pushpin.setTitle("Here collision expected");

        MapFlyout flyout = new MapFlyout();
        flyout.setTitle("Traffic");
        flyout.setDescription("Collision expected");
        pushpin.setFlyout(flyout);

        pinLayer.getElements().add(pushpin);
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    public void displayDeparturePinpoint(Geoposition geoposition) {
        MapIcon pushpin = new MapIcon();
        pushpin.setLocation(new Geopoint(geoposition));
        pushpin.setTitle("Departure point");

        // Load your custom vector drawable
        Drawable vectorDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_location_on_24_start);
        if (vectorDrawable != null) {
            // Convert the Drawable to a Bitmap
            Bitmap pinBitmap = drawableToBitmap(vectorDrawable);

            // Set the Bitmap as the icon for the MapIcon
            pushpin.setImage(new MapImage((pinBitmap)));
            //pushpin.setNormalizedAnchorPoint(new PointF(0.5f, 1.0f));  // Center against the bottom of the image
            pinLayer.getElements().add(pushpin);
        } else {
            Log.e("MapFragment", "Failed to load vector drawable");
        }

    }


    public void displayArrivalPinpoint(Geoposition geoposition) {
        MapIcon pushpin = new MapIcon();
        pushpin.setLocation(new Geopoint(geoposition));
        pushpin.setTitle("Arrival point");
        Drawable vectorDrawable  = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_location_off_24_end);
        Bitmap pinBitmap = Bitmap.createBitmap(
                vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );

        // Create a Canvas and draw the vector drawable onto the Bitmap
        Canvas canvas = new Canvas(pinBitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);

        // Set the Bitmap as the icon for the MapIcon
        pushpin.setImage(new MapImage(pinBitmap));
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