package com.off.on.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

	public static List<NameValuePair> nameValuePairsRequest(double myLatitude,
			double myLongitude, double myAltitude, float myAccuracy,
			boolean isGPSProvider, boolean isNetworkProvider, double myAzimuth,
			double myPitch, double myRoll) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair(
				Constants.WebServerLatitudeTag, Double.toString(myLatitude)));
		nameValuePairs.add(new BasicNameValuePair(
				Constants.WebServerLongitudeTag, Double.toString(myLongitude)));
		nameValuePairs.add(new BasicNameValuePair(
				Constants.WebServerAltitudeTag, Double.toString(myAltitude)));
		nameValuePairs.add(new BasicNameValuePair(
				Constants.WebServerAccuracyTag, Float.toString(myAccuracy)));
		nameValuePairs.add(new BasicNameValuePair(
				Constants.WebServerGPSProviderTag, Boolean
						.toString(isGPSProvider)));
		nameValuePairs.add(new BasicNameValuePair(
				Constants.WebServerNetworkProviderTag, Boolean
						.toString(isNetworkProvider)));
		nameValuePairs.add(new BasicNameValuePair(
				Constants.WebServerAzimuthTag, Double.toString(myAzimuth)));
		nameValuePairs.add(new BasicNameValuePair(Constants.WebServerRollTag,
				Double.toString(myPitch)));
		nameValuePairs.add(new BasicNameValuePair(Constants.WebServerPitchTag,
				Double.toString(myRoll)));
		return nameValuePairs;
	}

	public static boolean isNetworkAvailable(Context onContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) onContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
