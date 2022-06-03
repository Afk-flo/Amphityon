package com.example.amphy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.NumericShaper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Plats extends AppCompatActivity {

    /*
    Bundle extra = getIntent().getExtras();
    String user = extra.getString("user");
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plats);


        try {
            recupListe();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Button buttonIn = findViewById(R.id.buttonIN);
        Log.d("test", (String) buttonIn.getText());
        buttonIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Plats.this, CreationPlat.class);
              //  intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });

        final Button buttonOut = findViewById(R.id.buttonQuitter);
        Log.d("test", (String) buttonOut.getText());
        buttonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Plats.this, MenuChefCuisine.class);
                // intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });

    }

    // Récupération des plats
    public void recupListe() throws JSONException {

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

                try{
                    jsonArrayPlats = new JSONArray(responseStr);

                    for(int i = 0; i < jsonArrayPlats.length(); i++) {
                        JSONObject jsonPlat;
                        jsonPlat = jsonArrayPlats.getJSONObject(i);
                        // int id = json_data.getInt("IDPLAT");
                        arrayListePlats.add(jsonPlat.getString("NOMPLAT"));
                    }

                } catch (JSONException e) {
                    Log.d("ERREUR RECUP JSON", e.getMessage());
                    e.printStackTrace();
                }

                ListView lv = (ListView) findViewById(R.id.liste);
                ArrayAdapter<String> arrayAdapterServeur = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayListePlats);

                runOnUiThread(() -> {
                    lv.setAdapter(arrayAdapterServeur);
                });

                JSONArray finalJsonArrayPlats = jsonArrayPlats;
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            JSONObject plat = (JSONObject) finalJsonArrayPlats.getJSONObject(i);
                            Log.d("PLAT DE LA FIN", String.valueOf(plat));
                            Intent intent = new Intent(Plats.this, PlatUnique.class);
                            String user = getIntent().getStringExtra("user");
//                            intent.putExtra("user", user.toString());
                            intent.putExtra("plat", plat.toString());
                            startActivity(intent);

                        } catch (JSONException e) {
                            Log.d("ERREUR QUASI FIN", e.getMessage());
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }
}