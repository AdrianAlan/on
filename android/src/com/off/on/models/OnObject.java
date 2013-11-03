package com.off.on.models;

import android.os.Parcel;
import android.os.Parcelable;

public class OnObject implements Parcelable {

	private int id;
	private String info, name, category;
	private double longitude, latitude, altitude;

	public static final Parcelable.Creator<OnObject> CREATOR = new Parcelable.Creator<OnObject>() {
		public OnObject createFromParcel(Parcel in) {
			return new OnObject(in);
		}

		public OnObject[] newArray(int size) {
			return new OnObject[size];
		}
	};

	public OnObject(int id, String info, String name, String category,
			double longitude, double latitude, double altitude) {
		this.id = id;
		this.info = info;
		this.name = name;
		this.category = category;
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
	}

	public OnObject(Parcel in) {
		id = in.readInt();
		info = in.readString();
		name = in.readString();
		category = in.readString();
		longitude = in.readDouble();
		latitude = in.readDouble();
		altitude = in.readDouble();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(info);
		dest.writeString(name);
		dest.writeString(category);
		dest.writeDouble(longitude);
		dest.writeDouble(latitude);
		dest.writeDouble(altitude);
	}

	public int getId() {
		return id;
	}

	public String getInfo() {
		return info;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getAltitude() {
		return altitude;
	}
}
