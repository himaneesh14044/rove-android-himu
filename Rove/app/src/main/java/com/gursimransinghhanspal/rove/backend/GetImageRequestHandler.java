package com.gursimransinghhanspal.rove.backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class GetImageRequestHandler {
    private static final String PROTOCOL = "http";
    private static final String ADDRESS = "192.168.0.103";
    private static final int PORT = 3000;

    private Bitmap handleGetImageRequest(String imageUrl) {
        Bitmap img = null;

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            img = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    public Bitmap getImage(String imageUrl) {
        Bitmap img = null;
        String url = PROTOCOL + "://" + ADDRESS + ":" + String.valueOf(PORT) + imageUrl;

        GetImageRequestHandler.Request myTask = new GetImageRequestHandler.Request();

        try{
            img = myTask.execute(url).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return img;
    }

    private static class Request extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... args) {
            GetImageRequestHandler requestHandler = new GetImageRequestHandler();
            return requestHandler.handleGetImageRequest(args[0]);
        }

        @Override
        protected void onPostExecute(Bitmap img) {
            super.onPostExecute(img);
        }
    }
}
