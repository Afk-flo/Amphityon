package com.example.amphy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServiceUnique extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_unique);

        JSONObject plat;
        final String service = getIntent().getStringExtra("service");
        Log.d("EOEEOEDOEDOEDOOE", String.valueOf(service));

        try {
            plat = new JSONObject(getIntent().getStringExtra("plat"));
            System.out.println(plat);
            Log.d("PLATTTTTTTTTTTTTT", plat.toString());

            // On affiche les infos
            EditText prix = (EditText) this.findViewById(R.id.prixEdition);
            prix.setText(plat.getString("PRIXVENTE"));

            EditText qtte = (EditText) this.findViewById(R.id.qttEdition);
            qtte.setText(plat.getString("QUANTITEPROPOSEE"));

            // Btn de suppression
            final Button buttonDelete = findViewById(R.id.buttonSuppression);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        suppression(plat.getInt("IDPLAT"), service);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(ServiceUnique.this, Service.class);
                    startActivity(intent);
                }
            });

            final Button buttonGo = findViewById(R.id.buttonSauvegarde);
            buttonGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Float info = Float.parseFloat(String.valueOf(prix.getText()));
                        Log.d("plat", String.valueOf(plat.getInt("IDPLAT")));
                        Log.d("service", String.valueOf(service));
                        Log.d("info", String.valueOf(info));
                        Log.d("qtte", String.valueOf(Integer.parseInt(String.valueOf(qtte.getText()))));

                        miseAJour(plat.getInt("IDPLAT"),service , info, Integer.parseInt(String.valueOf(qtte.getText())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // La supprssion
    private void suppression(int id, String service) {
        String responseStr;
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("Request_METHOD", "DELETE")
                .add("idPlat", String.valueOf(id))
                .add("idService", service)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/back_end/api/cuisine.php")
                .delete(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("ERREUR DELETE", "Something went wrongly");

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseStr = response.body().toString();
                Log.d("SUCESS DELETE", responseStr);
            }
        });
    }

    // MAJ
    public void miseAJour(int idPlat, String idService, Float prix, int qtte) {
        String responseStr;
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("idPlat", String.valueOf(idPlat))
                .add("idService", String.valueOf(idService))
                .add("quantiteProposee", String.valueOf(qtte))
                .add("prixVente", String.valueOf(prix))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/back_end/api/cuisine.php")
                .put(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("ERREUR DELETE", "Something went wrongly");

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseStr = response.body().toString();
                Log.d("SUCESS DELETE", responseStr);
                Intent intent = new Intent(ServiceUnique.this, ServiceModification.class);
                startActivity(intent);
            }
        });
    }
}