package com.elgroup.lenovo.bottlepop.Utils;

import android.os.AsyncTask;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lenovo on 13-01-2016.
 */
public class GetLatLngAsyncTask extends AsyncTask<String, Void, String> {

    private CallBackInterface callBackInterface;

    public GetLatLngAsyncTask(CallBackInterface callBackInterface) {
        super();
        this.callBackInterface = callBackInterface;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        callBackInterface.onSuccess(string);
    }

    @Override
    protected String doInBackground(String... params) {
        String place = params[0];
        try {
            HttpURLConnection conn = null;
            StringBuilder jsonResults = new StringBuilder();
            String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="
                    + place + "&sensor=false";
            String urlString = googleMapUrl.replace(" ", "%20");
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(
                    conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            String a = "";
            return jsonResults.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
}
