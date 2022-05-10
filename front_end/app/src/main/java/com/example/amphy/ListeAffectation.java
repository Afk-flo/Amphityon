package com.example.amphy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_affectation);

        int idService=0;

        idService = getIntent().getIntExtra("idService",idService);

        try {
            Log.d("test","coucou");
            String unService = String.valueOf(idService);
            listeAffectation(unService);;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("test",e.getMessage());

        }
    }

    public void listeAffectation(String unService) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList listAffect = new ArrayList<String>();

        RequestBody formBody = new FormBody.Builder()
                .add("RequestMethod","POST")
                .add("demande","affectations")
                .add("idService", unService)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/apjy2/Amphityon/back_end/api/affectation.php/demande")
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
                        JSONObject jsonTable;
                        jsonTable = jsonArrayAffect.getJSONObject(i);
                        listAffect.add(jsonTable.getString("IDSERVICE"));
                        listAffect.add(jsonTable.getString("IDUSER1"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("test",e.getMessage());
                }



                ListView listViewTables = findViewById(R.id.listViewTables);

                ArrayAdapter<String> arrayAdapterAffect = new ArrayAdapter<String>(ListeAffectation.this, android.R.layout.simple_list_item_1, listAffect);

                runOnUiThread(() -> {
                    listViewTables.setAdapter(arrayAdapterAffect);
                });

                JSONArray finalJsonArrayTables = jsonArrayAffect;

                listViewTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try{
                            JSONObject uneAffect = finalJsonArrayTables.getJSONObject(i);
                            Intent intent = new Intent(ListeAffectation.this, AfficheTable.class);
                            intent.putExtra("uneAffect",uneAffect.toString());
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
