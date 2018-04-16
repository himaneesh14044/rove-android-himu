package com.gursimransinghhanspal.rove.backend;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GetRequestHandler {
    private static final String PROTOCOL = "http";
    private static final String ADDRESS = "192.168.1.3";
    private static final int PORT = 3000;

    private static String json = "";
    private static InputStream inputStream = null;
    private static JSONObject jsonObject = null;

    private JSONObject handleGetRequest(String url, List<NameValuePair> params) {
        StringBuilder urlBuilder = new StringBuilder(url);
        for (NameValuePair param : params) {
            urlBuilder.append(param.getValue()).append("/");
        }
        url = urlBuilder.toString();

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            inputStream.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jsonObject;
    }

    public JSONObject getJSON(String route, List<NameValuePair> params) {
        JSONObject jsonObject = null;
        String url = PROTOCOL + "://" + ADDRESS + ":" + String.valueOf(PORT) + route;

        Params param = new Params(url, params);
        Request myTask = new Request();

        try{
            jsonObject = myTask.execute(param).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private static class Params {
        String url;
        List<NameValuePair> params;


        Params(String url, List<NameValuePair> params) {
            this.url = url;
            this.params = params;

        }
    }

    private static class Request extends AsyncTask<Params, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(Params... args) {
            GetRequestHandler requestHandler = new GetRequestHandler();
            return requestHandler.handleGetRequest(args[0].url, args[0].params);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }
    }
}
