package com.booking.network;

import android.os.AsyncTask;

import com.booking.model.Response;
import com.booking.model.Cardlist;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LoadJSONTask extends AsyncTask<String, Void, Response> {

    public LoadJSONTask(Listener listener) {

        mListener = listener;
    }

    public interface Listener {

        void onLoaded(List<Cardlist> cardlists);

        void onError();
    }

    private Listener mListener;

    @Override
    protected Response doInBackground(String... strings) {
        try {
            String stringResponse = loadJSON(strings[0]);
            Gson gson = new Gson();
            return gson.fromJson("{ "+ "cards"+ ":" +stringResponse + "}", Response.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Response response) {

        if (response != null) {

            mListener.onLoaded(response.getCard());

        } else {

            mListener.onError();
        }
    }

    private String loadJSON(String jsonURL) throws IOException {

        URL url = new URL(jsonURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        boolean redirect = false;

        // normally, 3xx is redirect
        int status = conn.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                redirect = true;
        }
        System.out.println("Response Code ... " + status);
        if (redirect) {

            // get redirect url from "location" header field
            String newUrl = conn.getHeaderField("Location");

            // get the cookie if need, for login
            String cookies = conn.getHeaderField("Set-Cookie");

            // open the new connnection again
            conn = (HttpURLConnection) new URL(newUrl).openConnection();
            conn.setRequestProperty("Cookie", cookies);
            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.addRequestProperty("User-Agent", "Mozilla");
            conn.addRequestProperty("Referer", "google.com");

            System.out.println("Redirect to URL : " + newUrl);

        }

        // conn.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        StringBuilder response = new StringBuilder();

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        return response.toString();
    }
}