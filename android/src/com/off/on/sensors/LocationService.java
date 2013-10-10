/*
 * This service provides information about latitude, longitude, altitude and accuracy.
 * This service is running constantly in the background as the response of the application has to be immediate and the process of communicating with the server takes enough time already.
 */

package com.off.on.sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.off.on.utils.*;

public class LocationService extends Service {

	public String holdErrorInformation = "";

	private LocationManager onLocationManager = null;

	// define the time or distance intervals to update
	private static final int LOCATION_INTERVAL = 10000;
	private static final float LOCATION_DISTANCE = 10f;

	// variables initiated as negative to possibly detect no action taken by
	// this service in other processes.
	private double latitudeON = -1, longitudeON = -1, altitudeON = -1;
	private float accuracyON = -1;

	@Override
	public void onCreate() {
		super.onCreate();

		initLocationManager();

		try {
			onLocationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL,
					LOCATION_DISTANCE, onLocationListeners[1]);
		} catch (Exception e) {
			Log.e("onLocationManager",
					"Failed to request network location update" + e);
		}

		try {
			onLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, LOCATION_INTERVAL,
					LOCATION_DISTANCE, onLocationListeners[0]);
		} catch (Exception e) {
			Log.e("onLocationManager", "Failed to request gps location update"
					+ e);
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (onLocationManager != null) {
			for (int i = 0; i < onLocationListeners.length; i++) {
				try {
					onLocationManager.removeUpdates(onLocationListeners[i]);
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		sendGPSIntent();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void sendGPSIntent() {
		Intent onGPSIntent = new Intent();
		onGPSIntent.setAction(Constants.LocationActionTag);
		onGPSIntent.putExtra(Constants.LocationFlagLatitude, getLatitude());
		onGPSIntent.putExtra(Constants.LocationFlagLongitude, getLongitude());
		onGPSIntent.putExtra(Constants.LocationFlagAltitude, getAltitude());
		onGPSIntent.putExtra(Constants.LocationFlagAccuracy, getAccuracy());
		sendBroadcast(onGPSIntent);
	}

	private double getLatitude() {
		return latitudeON;
	}

	private double getLongitude() {
		return longitudeON;
	}

	private void setLatitude(double latitudeON) {
		this.latitudeON = latitudeON;
	}

	private void setLongitude(double longitudeON) {
		this.longitudeON = longitudeON;
	}

	public double getAltitude() {
		return altitudeON;
	}

	public void setAltitude(double altitudeON) {
		this.altitudeON = altitudeON;
	}

	public float getAccuracy() {
		return accuracyON;
	}

	public void setAccuracy(float accuracyON) {
		this.accuracyON = accuracyON;
	}

	private class LocationListener implements android.location.LocationListener {

		Location clientsLastLocation;

		public LocationListener(String ONProvider) {
			clientsLastLocation = new Location(ONProvider);
		}

		@Override
		public void onLocationChanged(Location location) {
			clientsLastLocation.set(location);
			setLongitude(clientsLastLocation.getLongitude());
			setLatitude(clientsLastLocation.getLatitude());
			setAccuracy(clientsLastLocation.getAccuracy());
			setAltitude(clientsLastLocation.getAltitude());
		}

		@Override
		public void onProviderDisabled(String ONProvider) {
		}

		@Override
		public void onProviderEnabled(String ONProvider) {
		}

		@Override
		public void onStatusChanged(String ONProvider, int status, Bundle extras) {
		}
	}

	LocationListener[] onLocationListeners = new LocationListener[] {
			new LocationListener(LocationManager.GPS_PROVIDER),
			new LocationListener(LocationManager.NETWORK_PROVIDER) };

	private void initLocationManager() {
		if (onLocationManager == null) {
			onLocationManager = (LocationManager) getApplicationContext()
					.getSystemService(Context.LOCATION_SERVICE);
		}
	}
}