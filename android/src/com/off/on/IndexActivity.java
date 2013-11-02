package com.off.on;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import com.off.on.communication.WebSpeaker;
import com.off.on.sensors.LocationService;
import com.off.on.sensors.OrientationService;
import com.off.on.utils.Constants;
import com.off.on.utils.JSONParser;
import com.off.on.utils.Utils;
import com.on.off.models.Objects;

public class IndexActivity extends Activity {

	private SensorsReceiver onStateReceiver;
	private JSONReceiver onJSONReceiver;

	private boolean isOrientationReceived = false, isLocationReceived = false,
			uniqueBoolean = true;
	private int maxDelay = 10;
	private Handler myHandler = new Handler();

	private double onLatitude, onLongitude, onAltitude, onAzimuth, onPitch,
			onRoll;
	private float onAccuracy;
	private boolean networkProvider, gpsProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (Utils.isNetworkAvailable(this)) {
			final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Toast.makeText(getApplicationContext(), Constants.GPSisOFF,
						Toast.LENGTH_SHORT).show();
			}
			myHandler.postDelayed(onNoResult, 1000 * maxDelay);
			myHandler.postDelayed(repeatRequest, 1000 * maxDelay / 4);

			setContentView(R.layout.activity_loading);
		} else {
			Toast.makeText(getApplicationContext(), Constants.InternetIsOFF,
					Toast.LENGTH_LONG).show();
			finishApp();
		}
	}

	@Override
	protected void onStart() {
		onStateReceiver = new SensorsReceiver();
		IntentFilter intentLocationFilter = new IntentFilter();
		intentLocationFilter.addAction(Constants.LocationActionTag);
		intentLocationFilter.addAction(Constants.OrientationActionTag);
		registerReceiver(onStateReceiver, intentLocationFilter);

		onJSONReceiver = new JSONReceiver();
		IntentFilter intentJSONFilter = new IntentFilter();
		intentJSONFilter.addAction(Constants.JSONActionTag);
		intentJSONFilter.addAction(Constants.JSONErrorActionTag);
		registerReceiver(onJSONReceiver, intentJSONFilter);

		getCurrentState(true, true);
		super.onStart();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(onStateReceiver);
		unregisterReceiver(onJSONReceiver);
		super.onPause();
	}

	private class SensorsReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent onReceivedIntent) {

			if (onReceivedIntent.getAction()
					.equals(Constants.LocationActionTag)) {
				isLocationReceived = true;

				onLatitude = onReceivedIntent.getDoubleExtra(
						Constants.LocationFlagLatitude, 0);
				onLongitude = onReceivedIntent.getDoubleExtra(
						Constants.LocationFlagLongitude, 0);
				onAltitude = onReceivedIntent.getDoubleExtra(
						Constants.LocationFlagAltitude, 0);
				onAccuracy = onReceivedIntent.getFloatExtra(
						Constants.LocationFlagAccuracy, 0);
				gpsProvider = onReceivedIntent.getBooleanExtra(
						Constants.LocationGPSProvider, false);
				networkProvider = onReceivedIntent.getBooleanExtra(
						Constants.LocationNetworkProvider, false);
			}

			if (onReceivedIntent.getAction().equals(
					Constants.OrientationActionTag)) {
				isOrientationReceived = true;

				onAzimuth = onReceivedIntent.getDoubleExtra(
						Constants.OrientationFlagAzimuth, 0);
				onPitch = onReceivedIntent.getDoubleExtra(
						Constants.OrientationFlagPitch, 0);
				onRoll = onReceivedIntent.getDoubleExtra(
						Constants.OrientationFlagRoll, 0);
			}

			if (isOrientationReceived && isLocationReceived) {

				Toast.makeText(
						getApplicationContext(),
						"Latitude: " + String.valueOf(onLatitude)
								+ "; Longitude: " + String.valueOf(onLongitude)
								+ "; Altitude: " + String.valueOf(onAltitude)
								+ "; Accuracy: " + String.valueOf(onAccuracy)
								+ "; GPS: " + gpsProvider + "; Network: "
								+ networkProvider + "; Azimuth: "
								+ String.valueOf(onAzimuth) + "; Roll "
								+ String.valueOf(onRoll) + "; Pitch: "
								+ onPitch, Toast.LENGTH_LONG).show();

				if (uniqueBoolean) {
					new WebSpeaker(getApplicationContext(),
							Utils.nameValuePairsRequest(onLatitude,
									onLongitude, onAltitude, onAccuracy,
									gpsProvider, networkProvider, onAzimuth,
									onPitch, onRoll))
							.execute(Constants.WebServerURL);
					uniqueBoolean = false;
				}

				myHandler.removeCallbacks(repeatRequest);

			}
		}
	}

	private class JSONReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context onContext, Intent onReceivedIntent) {
			if (onReceivedIntent.getAction().equals(Constants.JSONActionTag)) {
				JSONParser onJSON = new JSONParser(
						onReceivedIntent.getStringExtra(Constants.JSON));
				if (onJSON.readType().equals(Constants.JSONResponse)) {
					if (onJSON.readCount() > 0) {
						startMap(onJSON.readTriangle(), onJSON.readObjects());
					} else {
						Toast.makeText(getApplicationContext(),
								Constants.NoResults, Toast.LENGTH_LONG).show();
					}
				}
			} else if (onReceivedIntent.getAction().equals(
					Constants.JSONErrorActionTag)) {
				if (onReceivedIntent
						.getBooleanExtra(Constants.JSONError, false)) {
					Toast.makeText(onContext, Constants.CommunicationError,
							Toast.LENGTH_LONG).show();
				}
			}
			myHandler.removeCallbacks(onNoResult);
			finishApp();
		}
	}

	private void getCurrentState(boolean locationService,
			boolean orientationService) {
		if (locationService) {
			startService(new Intent(getApplicationContext(),
					LocationService.class));
		}
		if (orientationService) {
			startService(new Intent(getApplicationContext(),
					OrientationService.class));
		}
	}

	private void finishApp() {
		this.finish();
	}

	private Runnable onNoResult = new Runnable() {
		@Override
		public void run() {
			Toast.makeText(getApplicationContext(), Constants.UnableToON,
					Toast.LENGTH_LONG).show();
			finishApp();
		}
	};

	private Runnable repeatRequest = new Runnable() {
		@Override
		public void run() {
			getCurrentState(true, true);
			myHandler.postDelayed(repeatRequest, 1000 * maxDelay / 4);
		}
	};

	private void startMap(ArrayList<ArrayList<Double>> onTrianglePoints,
			ArrayList<Objects> onDetailsPushed) {
		Intent startNewActivityIntent = new Intent(this, TabActivity.class);
		startNewActivityIntent.putExtra(Constants.onNewActivityTriangle,
				onTrianglePoints);
		startNewActivityIntent.putExtra(Constants.onNewActivityDetails,
				onDetailsPushed);
		startActivity(startNewActivityIntent);
	}
}
