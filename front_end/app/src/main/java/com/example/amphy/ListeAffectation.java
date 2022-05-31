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

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ListeAffectation extends AppCompatActivity {
    JSONObject user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_affectation);
        int idService=0;
        idService = getIntent().getIntExtra("idService",idService);

        int finalIdService = idService;

        Button btnAjout = findViewById(R.id.btnAjoutAffect);
        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListeAffectation.this, AjoutAffectation.class);
                intent.putExtra("idService", finalIdService);
                try {
                    JSONObject user = new JSONObject(getIntent().getStringExtra("user"));
                    intent.putExtra("user", user.toString());
                    Log.d("",user.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

        final Button btnQuitter = (Button)findViewById(R.id.btnQuitter);
        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListeAffectation.this.finish();
            }
        });


        try {
            Log.d("test","coucou");
            listeAffect(idService);
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.d("test",e.getMessage());
        }
    }

    public void listeAffect(int unService) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList listAffectation = new ArrayList<String>();
        RequestBody formBody = new FormBody.Builder()
                .add("REQUEST_METHOD","POST")
                .add("demande","lesAffectations")
                .add("idService", String.valueOf(unService))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/apjy2/Amphityon/back_end/api/affectation.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayAffect = null;
                Log.d("test",responseStr);
                try {
                    jsonArrayAffect = new JSONArray(responseStr);
                    for(int i=0; i<jsonArrayAffect.length();i++){
                        JSONObject jsonAffect;
                        jsonAffect = jsonArrayAffect.getJSONObject(i);
                        listAffectation.add(jsonAffect.getString("NOM"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("test",e.getMessage());
                }

                ListView listViewAffect = findViewById(R.id.listViewServeur);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ListeAffectation.this, android.R.layout.simple_list_item_1, listAffectation);

                runOnUiThread(() -> {
                    listViewAffect.setAdapter(arrayAdapter);
                });

                JSONArray finalJsonArrayTables = jsonArrayAffect;

                listViewAffect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        try{
                            int idService = 0;
                            idService = getIntent().getIntExtra("idService",idService);
                            JSONObject user = null;
                            user = new JSONObject(getIntent().getStringExtra("user"));

                            JSONObject uneAffect = finalJsonArrayTables.getJSONObject(position);
                            Intent intent = new Intent(ListeAffectation.this, AfficheAffect.class);
                            intent.putExtra("uneAffect",uneAffect.toString());
                            intent.putExtra("idService",idService);
                            intent.putExtra("user", user.toString());
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Test","erreur ! connexion échouée ou impossible");
            }
        });
    }

}
