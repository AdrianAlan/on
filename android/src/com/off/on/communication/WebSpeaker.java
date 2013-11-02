package com.off.on.communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.off.on.utils.Constants;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class WebSpeaker extends AsyncTask<String, Void, String> {

	private static final int REGISTRATION_TIMEOUT = 3, WAIT_TIMEOUT = 10;
	private final HttpClient onHTTPClient = new DefaultHttpClient();
	private final HttpParams onPassParams = onHTTPClient.getParams();
	private HttpResponse onServerResponse;
	private String onResponseJSON = null;
	private boolean returnError = false;
	private Context onContext;
	private List<NameValuePair> onComRequest;

	public WebSpeaker(Context context, List<NameValuePair> request) {
		this.onContext = context;
		this.onComRequest = request;
	}

	// Magic happens here
	protected String doInBackground(String... s) {
		String URL = s[0];
		try {
			HttpConnectionParams.setConnectionTimeout(onPassParams,
					REGISTRATION_TIMEOUT * 1000);
			HttpConnectionParams
					.setSoTimeout(onPassParams, WAIT_TIMEOUT * 1000);
			ConnManagerParams.setTimeout(onPassParams, WAIT_TIMEOUT * 1000);
			HttpPost httpPost = new HttpPost(URL);
			httpPost.setEntity(new UrlEncodedFormEntity(onComRequest));
			// Main thing
			onServerResponse = onHTTPClient.execute(httpPost);
			StatusLine statusLine = onServerResponse.getStatusLine();
			// Check the HTTP Request
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				onServerResponse.getEntity().writeTo(out);
				out.close();
				onResponseJSON = out.toString();
			} else {
				// Closes the connection
				onServerResponse.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (Exception e) {
			Log.e(Constants.Error, "Server communication error" + e);
			returnError = true;
			cancel(true);
		}
		return onResponseJSON;
	}

	protected void onPostExecute(String content) {
		Intent onCommunicationIntent = new Intent();
		if (returnError) {
			onCommunicationIntent.setAction(Constants.JSONErrorActionTag);
			onCommunicationIntent.putExtra(Constants.JSONError, returnError);
		} else {
			onCommunicationIntent.setAction(Constants.JSONActionTag);
			onCommunicationIntent.putExtra(Constants.JSON, onResponseJSON);
		}
		onContext.sendBroadcast(onCommunicationIntent);
	}
}
