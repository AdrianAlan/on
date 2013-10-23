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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

public class OnMapFragment extends SupportMapFragment {

	private GoogleMap map;
	private static View view;
	private final static LatLng SOC = new LatLng(1.295441, 103.773497);

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
		}
	}

	private void addMarkers() {
		// Add info marker - it is possible to draw a canvas on info markers
		map.addMarker(new MarkerOptions()
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
