package com.off.on.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.off.on.TabActivity;
import com.off.on.models.OnObject;
import com.off.on.models.Triangle;
import com.off.on.utils.Constants;

public class OnMapFragment extends SupportMapFragment {

	private GoogleMap map;
	private static View view;

	private Triangle onTriangle;
	private ArrayList<OnObject> onObjects;
	private HashMap<String, Integer> markerMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = super.onCreateView(inflater, container, savedInstanceState);
		onTriangle = getArguments().getParcelable(Constants.onNewActivityTriangle);
		onObjects = getArguments().getParcelableArrayList(Constants.onNewActivityDetails);
		setUpMap();
		return view;
	}

	private void setUpMap() {
		if (map == null) {
			map = getMap();
			addMarkers();
			drawPolygon();
			moveCamera();
			map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				@Override
				public void onInfoWindowClick(Marker marker) {
					int objectsId = markerMap.get(marker.getId());
					for (OnObject onObject : onObjects) {
						if (objectsId == onObject.getId()) {
							((TabActivity) getActivity()).requestDetailedView(onObject);
							break;
						}
					}
				}
			});
		}
	}

	private void addMarkers() {
		markerMap = new HashMap<String, Integer>();
		MarkerOptions marker;
		for (OnObject onObject : onObjects) {
			marker = new MarkerOptions()
					.position(new LatLng(onObject.getLatitude(), onObject.getLongitude()))
					.title(onObject.getName())
					.snippet(onObject.getCategory() + ": " + onObject.getInfo());
			markerMap.put(map.addMarker(marker).getId(), onObject.getId());
		}
	}

	private void drawPolygon() {
		map.addPolygon(new PolygonOptions().addAll(createTriangle())
				.fillColor(Color.argb(64, 255, 0, 0)).strokeColor(Color.BLACK)
				.strokeWidth(1));
	}

	private void moveCamera() {
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(onTriangle.getLatitude(), onTriangle.getLongitude()))
				.zoom(19)
				.bearing((float) onTriangle.getBearing())
				.tilt(30).build();
		map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}

	private List<LatLng> createTriangle() {
		return Arrays.asList(
					new LatLng(onTriangle.getLatitude(), onTriangle.getLongitude()),
					new LatLng(onTriangle.getPointOneLat(), onTriangle.getPointOneLong()),
					new LatLng(onTriangle.getPointTwoLat(), onTriangle.getPointTwoLong())
				);
	}
}
