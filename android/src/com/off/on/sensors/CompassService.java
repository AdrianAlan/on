/*
 * This is a playground
 * NOT implemented yet
 */



package com.off.on.sensors;



import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class CompassService extends Service implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor sensorAccelerometer;
	private Sensor sensorMagneticField;

	private float[] valuesAccelerometer;
	private float[] valuesMagneticField;

	private float[] matrixR;
	private float[] matrixI;
	private float[] matrixValues;

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		switch (arg0.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			for (int i = 0; i < 3; i++) {
				valuesAccelerometer[i] = arg0.values[i];
			}
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			for (int i = 0; i < 3; i++) {
				valuesMagneticField[i] = arg0.values[i];
			}
			break;
		}

		boolean success = SensorManager.getRotationMatrix(matrixR, matrixI,
				valuesAccelerometer, valuesMagneticField);

		if (success) {
			SensorManager.getOrientation(matrixR, matrixValues);

			/*			double azimuth = Math.toDegrees(matrixValues[0]);
			double pitch = Math.toDegrees(matrixValues[1]);
			double roll = Math.toDegrees(matrixValues[2]);

			readingAzimuth.setText("Azimuth: " + String.valueOf(azimuth));
			readingPitch.setText("Pitch: " + String.valueOf(pitch));
			readingRoll.setText("Roll: " + String.valueOf(roll));

			myCompass.update(matrixValues[0]);
*/
		}

	}

	@Override
	public IBinder onBind(Intent arg0) {
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
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		sensorManager.unregisterListener(this, sensorAccelerometer);
		sensorManager.unregisterListener(this, sensorMagneticField);
		return super.onUnbind(intent);
	}

}
