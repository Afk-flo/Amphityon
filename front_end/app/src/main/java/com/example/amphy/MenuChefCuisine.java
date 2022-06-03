package com.example.amphy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MenuChefCuisine extends AppCompatActivity {
    String responseStr;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chefcuisine);

        String user;
        Bundle extra = getIntent().getExtras();
        user = extra.getString("user");

        final Button buttonPlats = findViewById(R.id.btnPlats);
        buttonPlats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Direction les plats
                Intent intent = new Intent(MenuChefCuisine.this, Plats.class);
                intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });


        final Button buttonService = (Button)findViewById(R.id.btnService);
        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuChefCuisine.this, ChoixServicePremier.class);
                intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });

        final Button buttonQtt = (Button)findViewById(R.id.btnQtt);
        buttonQtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuChefCuisine.this, ChoixServiceDeux.class);
                intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });

        }
    }
