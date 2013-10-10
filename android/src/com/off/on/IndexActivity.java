package com.off.on;

import com.off.on.sensors.LocationService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.off.on.utils.*;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class IndexActivity extends Activity {

	private ToggleButton ONorOFF;
	private TextView mainText;
	private MyReceiver myReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);

		ONorOFF = (ToggleButton) findViewById(R.id.toggleButtonOnOff);
		mainText = (TextView) findViewById(R.id.mainText);

		ONorOFF.setChecked(true);
		ONorOFF.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (!ONorOFF.isChecked()) {
					Toast.makeText(getApplicationContext(), "Refreshed",
							Toast.LENGTH_SHORT).show();
					startService(new Intent(getApplicationContext(),
							LocationService.class));
				}
			}
		});
	}

	@Override
	protected void onStart() {
		myReceiver = new MyReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.LocationActionTag);
		registerReceiver(myReceiver, intentFilter);
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(myReceiver);
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		startService(new Intent(getApplicationContext(), LocationService.class));
		super.onResume();
	}

	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			double lat = arg1
					.getDoubleExtra(Constants.LocationFlagLatitude, -1);
			double lon = arg1.getDoubleExtra(Constants.LocationFlagLongitude,
					-1);
			double alt = arg1
					.getDoubleExtra(Constants.LocationFlagAltitude, -1);
			float acc = arg1.getFloatExtra(Constants.LocationFlagAccuracy, -1);
			mainText.setText("Latitude: " + String.valueOf(lat) + "; Longitude: "
					+ String.valueOf(lon) + "; Altitude: " + String.valueOf(alt)
					+ "; Accuracy: " + String.valueOf(acc));
		}
	}
}
