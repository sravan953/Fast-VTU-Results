package com.teamidentiti.fastvturesults.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.teamidentiti.fastvturesults.R;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Intent i = getIntent();
            String data = i.getDataString();
            if(data.contains("/check_new_results"))
                data = data.replace("http://www.fastvturesults.com/check_new_results/", "");
            data=data.trim();
            String USN = data.substring(0, data.length());
            Log.d("DATA", USN);
            checkUSN(USN);
        } catch(Exception e) {}

        setToolbar();
        setUSNText();

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    private void setUSNText() {
        SharedPreferences p=PreferenceManager.getDefaultSharedPreferences(this);
        String USN=p.getString("USN", "");
        if(!USN.isEmpty())
            ((EditText)findViewById(R.id.editUSN)).setText(USN);
    }

    @Override
    public void onClick(View v) {
        EditText editText=(EditText)findViewById(R.id.editUSN);
        String USN=editText.getText().toString().trim();
        checkUSN(USN);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        }
    }

    private void checkUSN(String USN) {
        if(USN.equals("")||USN.length()!=10)
            Toast.makeText(this, "Enter a valid USN", Toast.LENGTH_LONG).show();
        else {
            saveUSN(USN);
            Intent i=new Intent(this, MainActivity2.class);
            i.putExtra("USN", USN);
            startActivity(i);
        }
    }

    private void saveUSN(String USN) {
        SharedPreferences p= PreferenceManager.getDefaultSharedPreferences(this);
        p.edit().putString("USN", USN).commit();
    }
}