package com.example.amphy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuChefSalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chefsalle);


        final Button buttonTables = findViewById(R.id.btnTable);
        buttonTables.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuChefSalle.this, Menu_Table.class);
                startActivity(intent);
            }
        }));

        final Button buttonServeur = findViewById(R.id.btnServeur);
        buttonTables.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuChefSalle.this, Menu_Table.class);
                startActivity(intent);
            }
        }));

        final Button buttonAffect = findViewById(R.id.btnAffect);
        buttonTables.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuChefSalle.this, Menu_Table.class);
                startActivity(intent);
            }
        }));
    }
}