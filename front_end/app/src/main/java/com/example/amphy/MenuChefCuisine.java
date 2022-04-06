package com.example.amphy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuChefCuisine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chefsalle);

        String user;
        Bundle extra = getIntent().getExtras();
        user = extra.getString("user");

        final Button buttonPlats = (Button)findViewById(R.id.btnPlats);
        buttonPlats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MenuChefCuisine.this, Plats.class);
                    intent.putExtra("user", user.toString());
                    startActivity(intent);
                }
            });

        final Button buttonService = (Button)findViewById(R.id.btnService);
        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuChefCuisine.this, Service.class);
                intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });

        final Button buttonQtt = (Button)findViewById(R.id.btnQtt);
        buttonQtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuChefCuisine.this, Plats.class);
                intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });

        }
    }
