package com.off.on.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Triangle implements Parcelable {

	private double latitude;
	private double longitude;
	private double bearing;

	private double pointOneLat;
	private double pointOneLong;

	private double pointTwoLat;
	private double pointTwoLong;

	public static final Parcelable.Creator<Triangle> CREATOR = new Parcelable.Creator<Triangle>() {
		public Triangle createFromParcel(Parcel in) {
			return new Triangle(in);
		}

		public Triangle[] newArray(int size) {
			return new Triangle[size];
		}
	};

	public Triangle(double latitude, double longitude, double bearing,
			double pointOneLat, double pointOneLong, double pointTwoLat,
			double pointTwoLong) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.bearing = bearing;
		this.pointOneLat = pointOneLat;
		this.pointOneLong = pointOneLong;
		this.pointTwoLat = pointTwoLat;
		this.pointTwoLong = pointTwoLong;
	}

	public Triangle(Parcel in) {
		latitude = in.readDouble();
		longitude = in.readDouble();
		bearing = in.readDouble();
		pointOneLat = in.readDouble();
		pointOneLong = in.readDouble();
		pointTwoLat = in.readDouble();
		pointTwoLong = in.readDouble();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeDouble(bearing);
		dest.writeDouble(pointOneLat);
		dest.writeDouble(pointOneLong);
		dest.writeDouble(pointTwoLat);
		dest.writeDouble(pointTwoLong);
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getBearing() {
		return bearing;
	}

	public double getPointOneLat() {
		return pointOneLat;
	}

	public double getPointOneLong() {
		return pointOneLong;
	}

	public double getPointTwoLat() {
		return pointTwoLat;
	}

	public double getPointTwoLong() {
		return pointTwoLong;
	}

}
