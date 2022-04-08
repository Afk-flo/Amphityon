package com.example.amphy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Plats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plats);

        String user;
        Bundle extra = getIntent().getExtras();
        user = extra.getString("user");

        try {
            ArrayList<String> laListe = new ArrayList<String>(extra.getStringArrayList("plats"));

            final ListView lv = (ListView) findViewById(R.id.liste);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, laListe );
            lv.setAdapter(arrayAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(Plats.this, PlatUnique.class);
                    intent.putExtra("user", user.toString());
                    JSONObject fin = null;
                    try {
                        fin = jsonPlat.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("platUnique", fin.toString());
                    Log.d("Plat:", fin.toString());
                    startActivity(intent);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Button buttonIn = findViewById(R.id.buttonIN);
        Log.d("test", (String) buttonIn.getText());
        buttonIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Plats.this, Plats.class);
                intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });

        final Button buttonOut = findViewById(R.id.buttonQuitter);
        Log.d("test", (String) buttonOut.getText());
        buttonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Plats.this, MenuChefCuisine.class);
                intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });


    }

}