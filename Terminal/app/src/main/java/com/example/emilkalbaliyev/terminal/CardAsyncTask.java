package com.example.emilkalbaliyev.terminal;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by EmilKelbali on 3/16/18.
 */

public class CardAsyncTask extends AsyncTask<Void, String, Response> {//change generic parameters
    private MainActivity mActivity;

    public CardAsyncTask(MainActivity activity){
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        try {
            MagneticCard.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Response doInBackground(Void... voids) {
        MagneticCard.startReading();
        String[] TracData = null;
        Response resp=null;
        while (!Thread.interrupted()) {
            try {
                TracData = MagneticCard.check(1000);
                Log.d("track:", TracData[0]);

                publishProgress(TracData);
                MagneticCard.startReading();
            } catch (Exception e) {
            }
        }
        return resp;
    }

    @Override
    protected void onProgressUpdate(String... strings) {
        JSONObject obj = new JSONObject();
        String[] arr = strings[0].split("\\^");
        Log.d("string", strings[0]);
        String pan = arr[0].substring(2, arr[0].length());
        String[] name = arr[1].split("/");
        String expdate = arr[2].substring(0, 4);
        String dd = arr[2].substring(7, arr[2].length() - 1);
        SharedPreferences sh1 = mActivity.getSharedPreferences("Account", mActivity.MODE_PRIVATE);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = new JSONObject();
            jsonObject.put("acc_id", sh1.getString("acc_id", ""));
            jsonObject.put("pan", pan);

            jsonObject.put("name", name[0]);
            jsonObject.put("surname", name[1]);
            jsonObject.put("expdate", expdate);
            jsonObject.put("dd", dd);

        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
        }
        Log.d("actv", "onProgressUpdate: before setjson ");

        mActivity.setJsonObject(jsonObject);

    }

    @Override
    protected void onPostExecute(Response response) {
        MagneticCard.close();
    }

//    protected Response process(String trackdata){
//        String[] arr = trackdata.split("\\^");
//        String pan = arr[0].substring(1, arr[0].length());
//        String[] name = arr[1].split(" ");
//        String expdate = arr[2].substring(0, 4);
//        String dd = arr[2].substring(7, arr[2].length() - 1);
//        SharedPreferences sh1 = mActivity.getSharedPreferences("Account", mActivity.MODE_PRIVATE);
//     //   Integer amnt = amount;
//
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            jsonObject = new JSONObject();
//            jsonObject.put("acc_id", sh1.getString("acc_id", ""));
//       //     jsonObject.put("amount", amnt);
//            jsonObject.put("pan", pan);
//            jsonObject.put("name", name[0]);
//            jsonObject.put("surname", name[1]);
//            jsonObject.put("expdate", expdate);
//            jsonObject.put("dd", dd);
//
//        } catch (JSONException e) {
//            Log.e("MYAPP", "unexpected JSON exception", e);
//        }
//
//        String stringUrl ="http://172.20.10.7:8080/payment/test";
//        String result;
//        String inputLine;
//        try {
//            URL myUrl = new URL(stringUrl);
//            HttpURLConnection connection =(HttpURLConnection)
//                    myUrl.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setReadTimeout(5000);
//            connection.setConnectTimeout(5000);
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(), "UTF-8") ;
//            wr.write(jsonObject.toString());
//            wr.flush();
//            wr.close();
//
//            InputStreamReader streamReader = new
//                    InputStreamReader(connection.getInputStream());
//            BufferedReader reader = new BufferedReader(streamReader);
//            StringBuilder stringBuilder = new StringBuilder();
//            while((inputLine = reader.readLine()) != null){
//                stringBuilder.append(inputLine);
//            }
//            reader.close();
//            streamReader.close();
//            result = stringBuilder.toString();
//        }
//        catch(Exception e){
//            Log.e("tag", e.getMessage(), e);
//            return Response.fail(e.getMessage());
//        }
//        Gson gson = new Gson();
//        return gson.fromJson(result, Response.class);
//
//    }
}
