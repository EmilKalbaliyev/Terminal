package com.example.emilkalbaliyev.terminal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText amount;
    private TextView pan, name;
    private JSONObject jsonObject;
    AsyncT asyncT;//new AsyncT(this);
    TextView info;
    CardAsyncTask cardAsyncTask;
    boolean isclean=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        pan = (TextView) findViewById(R.id.pan);
        name = (TextView) findViewById(R.id.name);
        amount = (EditText) findViewById(R.id.amount);
        info = findViewById(R.id.info);
        cardAsyncTask = new CardAsyncTask(this);
        cardAsyncTask.execute();
        Log.d("onCreate:", "before cardAsyncTask");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cardAsyncTask.cancel(true);
    }

    public void action(View v) {
        if(isclean) {
            asyncT = new AsyncT(this);
            String str_amnt = amount.getText().toString();
            Log.d("amount:", str_amnt);


            try {
                jsonObject.put("amount", str_amnt);
                Log.d("json:" +
                        "", jsonObject.getString("amount"));


            } catch (JSONException e) {
                Log.e("MYAPP", "unexpected JSON exception", e);
            }
            Log.d("chck:", "action: i am here");


            asyncT.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, jsonObject);
            isclean=false;
        }
        else{
            //startActivity(new Intent(MainActivity.this, MainActivity.class));
            pan.setText("");
            name.setText("");
            amount.setText("");
            info.setText("");
            info.setBackgroundColor(Color.TRANSPARENT);
          //  pan.setHint("PAN");
           // name.setHint("Full Name");



        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent act = new Intent(MainActivity.this, Settings.class);
        startActivity(act);

        return super.onOptionsItemSelected(item);
    }

    public void setJsonObject(JSONObject object) {
        jsonObject = object;
        try {
            pan.setText(jsonObject.getString("pan"));
            name.setText(jsonObject.getString("name") + " "+jsonObject.getString("surname"));
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
        }

    }

}
