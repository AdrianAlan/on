package com.off.on.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.on.off.models.Objects;

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

	public ArrayList<ArrayList<Double>> readTriangle() {
		ArrayList<ArrayList<Double>> onTriangleReturn = new ArrayList<ArrayList<Double>>();
		JSONObject jTempObject;
		ArrayList<Double> onTrianglePoint;
		try {
			onTrianglePoint = new ArrayList<Double>();
			jTempObject = jObject.getJSONObject(Constants.JSONPointZero);
			onTrianglePoint.add(jTempObject.getDouble(Constants.JSONLongitude));
			onTrianglePoint.add(jTempObject.getDouble(Constants.JSONLatitude));
			onTrianglePoint.add(jTempObject.getDouble(Constants.JSONBearing));
			onTriangleReturn.add(onTrianglePoint);
			onTrianglePoint = new ArrayList<Double>();
			jTempObject = jObject.getJSONObject(Constants.JSONPointOne);
			onTrianglePoint.add(jTempObject.getDouble(Constants.JSONLongitude));
			onTrianglePoint.add(jTempObject.getDouble(Constants.JSONLatitude));
			onTrianglePoint.add(jTempObject.getDouble(Constants.JSONBearing));
			onTriangleReturn.add(onTrianglePoint);
			onTrianglePoint = new ArrayList<Double>();
			jTempObject = jObject.getJSONObject(Constants.JSONPointTwo);
			onTrianglePoint.add(jTempObject.getDouble(Constants.JSONLongitude));
			onTrianglePoint.add(jTempObject.getDouble(Constants.JSONLatitude));
			onTrianglePoint.add(jTempObject.getDouble(Constants.JSONBearing));
			onTriangleReturn.add(onTrianglePoint);
		} catch (JSONException e) {
			Log.e(Constants.Error, "Error parsing data " + e.toString());
		}
		return onTriangleReturn;
	}

	public ArrayList<Objects> readObjects() {
		ArrayList<Objects> onObjects = new ArrayList<Objects>();
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

				onObjects.add(new Objects(id, info, name, category, longitude,
						latitude, altitude));
			}
		} catch (JSONException e) {
			Log.e(Constants.Error, "Error parsing data " + e.toString());
		}
		return onObjects;
	}
}
