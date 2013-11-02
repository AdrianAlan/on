package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {

	private int id;
	private String info;
	private String name;
	private String clz;
	private double longitude;
	private double latitude;
	private double altitude;

	public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
		public Result createFromParcel(Parcel in) {
			return new Result(in);
		}

		public Result[] newArray(int size) {
			return new Result[size];
		}
	};
	
	public Result(int id, String info, String name, String clz, double longitude, double latitude, double altitude) {
		this.id = id;
		this.info = info;
		this.name = name;
		this.clz = clz;
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
	}

	public Result(Parcel in) {
		id = in.readInt();
		info = in.readString();
		name = in.readString();
		clz = in.readString();
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
		dest.writeString(clz);
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

	public String getClz() {
		return clz;
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

