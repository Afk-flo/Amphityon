package com.example.amphy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoixServicePremier extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_service_premier);

        final Button midi = (Button) findViewById((R.id.btnMidi));
        midi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoixServicePremier.this, Service.class);
                intent.putExtra("service", "1");
                startActivity(intent);
            }
        });


        final Button soir = (Button) findViewById(R.id.btnSoir);
        soir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoixServicePremier.this, Service.class);
                intent.putExtra("service", "2");
                startActivity(intent);
            }
        });
    }
}