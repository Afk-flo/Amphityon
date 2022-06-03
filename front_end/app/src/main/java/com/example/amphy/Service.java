package com.example.amphy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.transform.sax.SAXResult;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Service extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        final String service = getIntent().getStringExtra("service");
        recuPlats();

        Button retour = (Button) findViewById(R.id.btnRetour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Service.this, MenuChefCuisine.class);
                startActivity(intent);
            }
        });

        Button envoie = (Button) findViewById(R.id.btnAjout);
        envoie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String responseStr;
                OkHttpClient client = new OkHttpClient();

                // Récup data
                Spinner plat = (Spinner) findViewById(R.id.spinner);
                String lePlat = String.valueOf(plat.getSelectedItem());

                final EditText prix = (EditText) findViewById(R.id.EditPrix);
                float lePrix = Float.parseFloat(String.valueOf(prix.getText()));

                final EditText qtte = (EditText)  findViewById(R.id.EditQtt);
                int laQtte = Integer.parseInt(String.valueOf(qtte.getText()));

                RequestBody formBody = new FormBody.Builder()
                        .add("nomPlat", lePlat)
                        .add("idService", service)
                        .add("quantiteProposee", String.valueOf(laQtte))
                        .add("prixVente", String.valueOf(lePrix))
                        .build();

                Request request = new Request.Builder()
                        .url("http://192.168.1.94/back_end/api/proposer.php")
                        .post(formBody)
                        .build();

                Call call = client.newCall(request);
                String finalPlat = lePlat;
                call.enqueue(new Callback() {

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d("Réussite, let's goooo", "t bg tooi un peu non?");
                        Log.d("QTTEE", String.valueOf(laQtte));
                        Log.d("PRIX", String.valueOf(lePrix));
                        Intent intent = new Intent(Service.this, Service.class);
                        intent.putExtra("service", service);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("Echec", "Impossible d'uploader les données");
                    }

                });

            }
        });
    }
    // Récupération des plats
    public void recuPlats() {

        String responseStr;
        OkHttpClient client = new OkHttpClient();

        ArrayList<String> arrayListePlats = new ArrayList<String>();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/back_end/api/cuisine.php")
                .get()
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Echec", "Impossible de récupérer les données");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayPlats = null;

                try {
                    jsonArrayPlats = new JSONArray(responseStr);

                    for (int i = 0; i < jsonArrayPlats.length(); i++) {
                        JSONObject jsonPlat;
                        jsonPlat = jsonArrayPlats.getJSONObject(i);
                        // int id = json_data.getInt("IDPLAT");
                        arrayListePlats.add(jsonPlat.getString("NOMPLAT"));
                    }

                } catch (JSONException e) {
                    Log.d("ERREUR RECUP JSON", e.getMessage());
                    e.printStackTrace();
                }

                // Adapter array for spinner
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayListePlats);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                runOnUiThread(() -> {
                    spinner.setAdapter(arrayAdapter);
                });

            }
        });
    }
}
