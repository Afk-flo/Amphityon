package com.example.amphy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PlatUnique extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plat_unique);

        String plat;
        Bundle extra = getIntent().getExtras();
        plat = extra.getString("plat");

        System.out.println(plat);
    }
}