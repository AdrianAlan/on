package com.off.on.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.off.on.models.OnObject;
import com.off.on.models.Triangle;

import android.util.Log;

public class JSONParser {

	private String JSON = "";
	private JSONObject jObject = null;

	public JSONParser(String inputString) {
		JSON = inputString;
		try {
			jObject = new JSONObject(JSON);
		} catch (JSONException e) {
			Log.e(Constants.Error,
					"Error parsing data from JSON " + e.toString());
		}
	}

	public String readType() {
		try {
			return jObject.getString(Constants.JSONType);
		} catch (JSONException e) {
			Log.e(Constants.Error, "Error parsing data " + e.toString());
		}
		return null;
	}

	public int readCount() {
		try {
			return jObject.getInt(Constants.JSONCount);
		} catch (JSONException e) {
			Log.e(Constants.Error, "Error parsing data " + e.toString());
		}
		return 0;
	}

	public Triangle readTriangle() {
		
		Triangle onTriangle = null;
		try {
			
			JSONObject jTempObject = jObject.getJSONObject(Constants.JSONPointZero);
			Double latitude = jTempObject
					.getDouble(Constants.JSONLatitude);
			Double longitude = jTempObject
					.getDouble(Constants.JSONLongitude);
			Double bearing = jTempObject
					.getDouble(Constants.JSONBearing);

			jTempObject = jObject.getJSONObject(Constants.JSONPointOne);
			Double pointOneLat = jTempObject
					.getDouble(Constants.JSONLatitude);
			Double pointOneLong = jTempObject
					.getDouble(Constants.JSONLongitude);
			
			jTempObject = jObject.getJSONObject(Constants.JSONPointTwo);
			Double pointTwoLat = jTempObject
					.getDouble(Constants.JSONLatitude);
			Double pointTwoLong = jTempObject
					.getDouble(Constants.JSONLongitude);

			onTriangle = new Triangle(latitude, longitude, bearing, pointOneLat, pointOneLong, pointTwoLat, pointTwoLong);
			
		} catch (JSONException e) {
			Log.e(Constants.Error, "Error parsing data " + e.toString());
		}
		return onTriangle;
	}

	public ArrayList<OnObject> readObjects() {
		ArrayList<OnObject> onObjects = new ArrayList<OnObject>();
		try {
			JSONArray JSONObjects = jObject.getJSONArray(Constants.JSONObjects);
			for (int i = 0; i < JSONObjects.length(); i++) {
				JSONObject onObject = JSONObjects.getJSONObject(i);
				int id = onObject.getInt(Constants.JSONObjectID);
				String info = onObject.getString(Constants.JSONObjectInfo);
				String name = onObject.getString(Constants.JSONObjectName);
				String category = onObject
						.getString(Constants.JSONObjectCategory);
				Double longitude = onObject
						.getDouble(Constants.JSONObjectLongitude);
				Double latitude = onObject
						.getDouble(Constants.JSONObjectLatitude);
				Double altitude = onObject
						.getDouble(Constants.JSONObjectAltitude);

				onObjects.add(new OnObject(id, info, name, category, longitude,
						latitude, altitude));
			}
		} catch (JSONException e) {
			Log.e(Constants.Error, "Error parsing data " + e.toString());
		}
		return onObjects;
	}
}
