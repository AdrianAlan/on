package com.on.off.models;

public class Objects {

	private int objectID;
	private String objectInfo, objectName, objectCategory;
	private double objectLongitude, objectLatitude, objectAltitude;

	public Objects(int iD, String info, String name, String category,
			double longitude, double latitude, double altitude) {
		this.objectID = iD;
		this.objectInfo = info;
		this.objectName = name;
		this.objectCategory = category;
		this.objectLongitude = longitude;
		this.objectLatitude = latitude;
		this.objectAltitude = altitude;
	}
}
