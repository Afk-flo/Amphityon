package com.example.amphy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu_ChoixService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_choix_service);

        final Button boutonValider = (Button) this.findViewById(R.id.boutonValider);

        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idService = 0;
                String option = "";
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId != -1){
                    RadioButton radioButtonOptionSelected = findViewById(selectedId);
                    option = radioButtonOptionSelected.getText().toString();
                    if(option.equals("Midi")) {
                        idService = 1;
                    }
                    else {
                        idService = 2;
                    }
                }


                Intent intent = new Intent (Menu_ChoixService.this, MenuChefSalle.class);
                intent.putExtra("idService",idService);
                try {
                    JSONObject user = new JSONObject(getIntent().getStringExtra("user"));
                    intent.putExtra("user", user.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }
}