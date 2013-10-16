package com.off.on.sensors;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

public class OrientationService extends Service implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor sensorAccelerometer, sensorMagneticField;
	private float[] valuesAccelerometer, valuesMagneticField;
	private float[] matrixR, matrixI, matrixValues;
	private String out = "";

	@Override
	public void onCreate() {
		Toast.makeText(getApplicationContext(), "Created Compass",
				Toast.LENGTH_LONG).show();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		sensorAccelerometer = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorMagneticField = sensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		valuesAccelerometer = new float[3];
		valuesMagneticField = new float[3];

		matrixR = new float[9];
		matrixI = new float[9];
		matrixValues = new float[3];

		sensorManager.registerListener(this, sensorAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this, sensorMagneticField,
				SensorManager.SENSOR_DELAY_NORMAL);

		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(getApplicationContext(), "Started Compass" + out,
				Toast.LENGTH_LONG).show();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			for (int i = 0; i < 3; i++) {
				valuesAccelerometer[i] = event.values[i];
			}
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			for (int i = 0; i < 3; i++) {
				valuesMagneticField[i] = event.values[i];
			}
			break;
		}

		try {
			boolean success = SensorManager.getRotationMatrix(matrixR, matrixI,
					valuesAccelerometer, valuesMagneticField);
			if (success) {
				SensorManager.getOrientation(matrixR, matrixValues);

				double azimuth = Math.toDegrees(matrixValues[0]);
				double pitch = Math.toDegrees(matrixValues[1]);
				double roll = Math.toDegrees(matrixValues[2]);

				out = String.format("Azimuth: %.2f\nPitch:%.2f\nRoll:%.2f",
						azimuth, pitch, roll);
			}
		} catch (Exception e) {

		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		sensorManager.unregisterListener(this, sensorAccelerometer);
		sensorManager.unregisterListener(this, sensorMagneticField);
		sensorManager.unregisterListener(this);
	}
}
