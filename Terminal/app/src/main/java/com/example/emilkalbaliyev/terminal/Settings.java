package com.example.emilkalbaliyev.terminal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by EmilKelbali on 3/5/18.
 */

public class Settings extends AppCompatActivity {
EditText acc_id;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        acc_id=(EditText)findViewById(R.id.acc_id);
        SharedPreferences sh1=getSharedPreferences("Account",MODE_PRIVATE);
        String s1=sh1.getString("acc_id","");
        acc_id.setText(s1);

    }

    public void save(View view) {

        SharedPreferences sh1=getSharedPreferences("Account",MODE_PRIVATE);
        SharedPreferences.Editor edit= sh1.edit();
        edit.putString("acc_id",acc_id.getText().toString());
        edit.commit();
        Toast.makeText(getApplicationContext(),"Account id is changed.",Toast.LENGTH_SHORT).show();
        Intent act= new Intent(Settings.this,MainActivity.class);
        startActivity(act);



    }
}
