package com.example.amphy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreationPlat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_plat);

        Button sauvegarder = (Button) findViewById(R.id.buttonEnvoie);

        sauvegarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creation();
            }
        });
    }

    // Obtention du radio
    /*
    @SuppressLint("NonConstantResourceId")
    public String onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        String categorie = "";
        switch (view.getId()) {
            case R.id.rEntree:
                categorie = "1";
                break;
            case R.id.rPlat:
                categorie = "2";
                break;
            case R.id.rDessert:
                categorie = "3";
                break;
            default:
                categorie = "1";
                break;
        }

        return categorie;
    }

     */


    // Creation en DB d'un PLAT
    public void creation() {

        String responseStr;
        OkHttpClient client = new OkHttpClient();

        final EditText nom = (EditText) findViewById(R.id.nomtext);
        final EditText description = (EditText) findViewById(R.id.desctext);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        String categorie = "";
        switch (selectedId) {
            case R.id.rEntree:
                categorie = "1";
                break;
            case R.id.rPlat:
                categorie = "2";
                break;
            case R.id.rDessert:
                categorie = "3";
                break;
            default:
                categorie = "1";
                break;
        }


        RequestBody formBody = new FormBody.Builder()
                .add("nomPlat", nom.getText().toString())
                .add("descriptif", description.getText().toString())
                .add("idCat", categorie.toString())
                .add("idUser", "2")
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/back_end/api/cuisine.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        String finalCategorie = categorie;
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("Réussite, let's goooo", "t bg tooi un peu non?");
                Log.d("nom",  nom.getText().toString());
                Log.d("descriptif",  description.getText().toString());
                Log.d("cat",  finalCategorie.toString());


            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Echec", "Impossible d'uploader les données");
            }
        });
    }
}