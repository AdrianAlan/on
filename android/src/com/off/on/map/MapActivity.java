package com.off.on.map;

import java.util.Arrays;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.off.on.R;

public class MapActivity extends FragmentActivity {

    private GoogleMap map;
    private final static LatLng SOC = new LatLng(1.295441, 103.773497);

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_results);
        setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    private void setUpMap() {
        if (map == null) {
        	map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (map != null) {
            	addMarkers();
            	drawPolygon();
            	moveCamera();
            }
        }

    }

    private void addMarkers() {
    	// Add info marker - it is possible to draw a canvas on info markers
    	Marker soc = map.addMarker(new MarkerOptions()
	        .position(new LatLng(SOC.latitude, SOC.longitude + 0.0005))
	        .title("School of Computing")
    		.snippet("Snippet about the location"));
	}
    
    private void drawPolygon() {
    	// Add triangular polygon
    	map.addPolygon(new PolygonOptions()
        .addAll(createTriangle(SOC, 0.0005, 0.0005))
        .fillColor(Color.argb(64, 255, 0, 0))
        .strokeColor(Color.BLACK)
        .strokeWidth(1));	
    }
    
    private void moveCamera() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
        	.target(SOC)
        	.zoom(19)
        	.bearing(90)
        	.tilt(30)
        	.build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    
    
    private List<LatLng> createTriangle(LatLng center, double halfWidth, double halfHeight) {
        return Arrays.asList(
                new LatLng(center.latitude, center.longitude),
                new LatLng(center.latitude - halfHeight/2, center.longitude + 2*halfWidth),
                new LatLng(center.latitude + halfHeight/2, center.longitude + 2*halfWidth));

    }
}
