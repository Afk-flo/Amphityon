package com.example.amphy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuChefSalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chefsalle);
        int idService=0;

        idService = getIntent().getIntExtra("idService",idService);

        final Button btnLesTables = findViewById(R.id.btnTable);
        final Button btnServeur = findViewById(R.id.btnServeur);
        final Button btnAffect = findViewById(R.id.btnQuitter);

        int finalService = idService;
        btnLesTables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuChefSalle.this, Menu_Table.class);
                intent.putExtra("demande","table");
                intent.putExtra("idService", finalService);
                try {
                    JSONObject user = new JSONObject(getIntent().getStringExtra("user"));
                    intent.putExtra("user", user.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

        btnServeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuChefSalle.this, ListeAffectation.class);
                intent.putExtra("demande","table");
                intent.putExtra("idService", finalService);
                try {
                    JSONObject user = new JSONObject(getIntent().getStringExtra("user"));
                    intent.putExtra("user", user.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

        btnAffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuChefSalle.this, AfficheTable.class);
                intent.putExtra("idService", finalService);
                startActivity(intent);
            }
        });

        final Button btnQuitter = (Button)findViewById(R.id.btnQuitter);
        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuChefSalle.this.finish();
            }
        });

        
    }
}