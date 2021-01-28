package com.example.pruebafirebase;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchBookRequest extends Thread {
    private String url;
    private Handler handler;

    public SearchBookRequest(String url, Handler handler) {
        this.url = url;
        this.handler = handler;
    }

    public void run() {
        try {
            URL address = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) address.openConnection();

            int code = connection.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK) {
                InputStream inputS = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputS));

                StringBuilder builder = new StringBuilder();
                String thisLine;

                while ((thisLine = br.readLine()) != null) {
                    builder.append(thisLine);
                }

                String json = builder.toString();


                JSONObject obj = new JSONObject(json);
                JSONArray arr = new JSONArray(obj.get("docs").toString());

                Message unMensaje = new Message();
                unMensaje.obj = arr;
                handler.sendMessage(unMensaje);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
