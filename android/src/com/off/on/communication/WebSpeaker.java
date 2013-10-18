package com.off.on.communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class WebSpeaker extends AsyncTask<String, Void, String> {
 
    private static final int REGISTRATION_TIMEOUT = 3, WAIT_TIMEOUT = 30;
    private final HttpClient onHTTPClient = new DefaultHttpClient();
    private final HttpParams onPassParams = onHTTPClient.getParams();
    private HttpResponse onServerResponse;
    private String onResponseJSON =  null;
    private boolean returnError = false;
    private Context onContext;
 
    public WebSpeaker(Context context){
        this.onContext = context;
    }
 
    protected void onPreExecute() {
    	Toast.makeText(onContext, "Started from the bottom", Toast.LENGTH_LONG).show();
    }
 
    //Magic happens here
    protected String doInBackground(String... s) {
 
        String URL = null;
        String nameparamr = "started";
        String paramvalue = "from-the-bottom";
 
        try {
            URL = s[0];
            HttpConnectionParams.setConnectionTimeout(onPassParams, REGISTRATION_TIMEOUT * 1000);
            HttpConnectionParams.setSoTimeout(onPassParams, WAIT_TIMEOUT * 1000);
            ConnManagerParams.setTimeout(onPassParams, WAIT_TIMEOUT * 1000);
 
            HttpPost httpPost = new HttpPost(URL);
 
            //Setting parameters
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair(nameparamr,paramvalue));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 
            //Main thing
            onServerResponse = onHTTPClient.execute(httpPost);
            StatusLine statusLine = onServerResponse.getStatusLine();
            
            //Check the HTTP Request
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                onServerResponse.getEntity().writeTo(out);
                out.close();
                onResponseJSON = out.toString();
            }
            else{
                //Closes the connection
                onServerResponse.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            Log.e("OnError:", "Server communication error" + e);
            returnError = true;
            cancel(true);
        }
        return onResponseJSON;
    }
 
    protected void onCancelled() {
    	Toast.makeText(onContext, "Error occured while talking to server!", Toast.LENGTH_LONG).show();
    }
 
    protected void onPostExecute(String content) {
        if (returnError) {
            Toast.makeText(onContext, "Error occured while talking to server!", Toast.LENGTH_LONG).show();
        } else {
        	Toast.makeText(onContext, onResponseJSON, Toast.LENGTH_LONG).show();
        }
    } 
}