package com.off.on.fragments;

import java.util.Arrays;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

public class OnMapFragment extends MapFragment {

	private GoogleMap map;
	private static View view;
	
	// Testing purposes
	private final static LatLng SOC = new LatLng(1.295441, 103.773497);
	private final static LatLng SOCFrontLeft = new LatLng(1.295441 - 0.00025, 103.773497 + 0.0005);
	private final static LatLng SOCFrontRight = new LatLng(1.295441 + 0.00025, 103.773497 + 0.0005);


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = super.onCreateView(inflater, container, savedInstanceState);
		setUpMap();
		return view;
	}

	private void setUpMap() {
		if (map == null) {
			map = getMap();
			addMarkers();
			drawPolygon();
			moveCamera();
            map.setOnInfoWindowClickListener(new OnInfoWindowClickListener()
            {
				@Override
				public void onInfoWindowClick(Marker arg0) {
                    getActivity().getActionBar().setSelectedNavigationItem(1);
                    // Pass information on which marker was clicked.
				}
            });  
		}
	}

	private void addMarkers() {
		map.addMarker(new MarkerOptions()
		.position(new LatLng(SOC.latitude, SOC.longitude + 0.00025))
		.title("School of Computing")
		.snippet("Snippet about the location."));
	}

	private void drawPolygon() {
		// Add triangular polygon
		map.addPolygon(new PolygonOptions()
		.addAll(createTriangle(SOC, SOCFrontLeft, SOCFrontRight))
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

	
	private List<LatLng> createTriangle(LatLng viewpoint, LatLng frontLeft, LatLng frontRight) {
		return Arrays.asList(viewpoint, frontLeft, frontRight);
	}
	
	
}
