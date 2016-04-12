package com.elgroup.lenovo.bottlepop.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lenovo on 04-12-2015.
 */
public class GlobalAsyncTask extends AsyncTask<ArrayList<NameValuePair>, Void, String> {

    private CallBackInterface myNotificationListener;
    private String urlString;
    private ProgressDialog progressDialog = null;
    private boolean isGetService = false;

    public GlobalAsyncTask(Context context, String urlString, CallBackInterface myNotificationListener) {
        super();
        this.myNotificationListener = myNotificationListener;
        this.urlString = urlString;
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressDialog != null)
            progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog != null)
            progressDialog.dismiss();

        if (!isGetService) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                String status = jsonObject.getString("status");
                if ("true".equals(status)) {
                    myNotificationListener.onSuccess(s);
                } else {
                    myNotificationListener.onFailure(jsonObject.getJSONArray(Keys.ERROR).getJSONObject(0).getString(Keys.MESSAGE));

                }
            } catch (JSONException e) {
                e.printStackTrace();
                myNotificationListener.onFailure(e.getMessage());

            }
        } else {
            myNotificationListener.onSuccess(s);
        }

    }

    @Override
    protected String doInBackground(ArrayList<NameValuePair>... params) {

        ArrayList<NameValuePair> list = params[0];
        String response = "";
        if (list.isEmpty()) {
            isGetService = true;
            response = HitGetWebService();
        } else {
            response = HitPostWebService(list);
        }


        return response;
    }


    private String HitGetWebService() {

        HttpClient httpClient = new DefaultHttpClient();
        urlString = urlString.replace(" ", "%20");
        try {
            HttpGet httpGet = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(httpGet);
            final String resString = EntityUtils.toString(response.getEntity());
            return resString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String HitPostWebService(ArrayList<NameValuePair> list) {

        String resString = "";

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlString);

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            resString = EntityUtils.toString(response.getEntity());
            Log.d("", "cityManager------city-data------" + resString);
            Log.d("", "entry_point-----2");
        } catch (IOException e) {

            e.printStackTrace();

        }
        return resString;
    }
}
