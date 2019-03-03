package com.example.emilkalbaliyev.terminal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmilKelbali on 3/9/18.
 */

public class AsyncT extends AsyncTask<JSONObject, Integer, Response> {

    private Activity activity;

    public AsyncT(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        TextView tw = activity.findViewById(R.id.info);
        tw.setBackgroundColor(Color.parseColor("#A0A0A0"));
        Log.d("async", "onPreExecute: ");
        tw.setText("processing...");
    }

    @Override
    protected Response doInBackground(JSONObject... objs) {
        String stringUrl = "http://192.168.56.1:8080/payment/test";
        Log.d("async:", "doInBackground: I AM IN ASYNCTASK DOIN");
        String result;
        String inputLine;
        JSONObject jsonObject = objs[0];
        try {
            URL myUrl = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection)
                    myUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setReadTimeout(5000);
            Log.e("async:", "doInBackground: Check log");
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            wr.write(jsonObject.toString());
            wr.flush();
            wr.close();
            Log.e("async:", "doInBackground: Check log2");

            int respCode = connection.getResponseCode();
            InputStream is = respCode == 200 ? connection.getInputStream() : connection.getErrorStream();
            InputStreamReader streamReader = new
                    InputStreamReader(is);
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();
            result = stringBuilder.toString();

            Gson gson = new Gson();
            return gson.fromJson(result, Response.class);
        } catch (Exception e) {
            Log.e("tag", e.getMessage(), e);
            return Response.fail(e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(Response response) {
        Log.d("tag", response.getMessage());
        TextView tw = activity.findViewById(R.id.info);
        tw.setBackgroundColor(response.isSuccess() ? ContextCompat.getColor(activity.getApplicationContext(), R.color.success) :
                ContextCompat.getColor(activity.getApplicationContext(), R.color.fail));
        tw.setText(response.getMessage());
    }
}

