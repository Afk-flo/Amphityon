package com.example.amphy;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

public class AjoutAffectation extends AppCompatActivity {
    JSONObject user;
    Spinner spinner;
    EditText editTextTable;
    private JSONObject serveur = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_affectation);
        spinner = findViewById(R.id.serveur);
        editTextTable = findViewById(R.id.table);

        try {
            user = new JSONObject(getIntent().getStringExtra("user"));
            importListAfect();
            importListTables();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        final Button buttonValiderMesInfos = findViewById(R.id.btnEnregistrer);
        buttonValiderMesInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ajoutAffect();
                    Intent intent = new Intent(AjoutAffectation.this, ListeAffectation.class);
                    startActivity(intent);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        final Button btnQuitter = (Button)findViewById(R.id.btnQuitter);
        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AjoutAffectation.this.finish();
            }
        });

    }

    public void importListAfect() throws IOException{
        ArrayList arrayListServeur = new ArrayList<String>();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("REQUEST_METHOD","POST")
                .add("demande","getServeurs")
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/apjy2/Amphityon/back_end/api/affectation.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call,@NonNull Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayServeur = null;
                Log.d("test",responseStr);
                try {
                    jsonArrayServeur = new JSONArray(responseStr);
                    for (int i = 0; i < jsonArrayServeur.length(); i++) {
                        JSONObject jsonServeur;
                        jsonServeur = jsonArrayServeur.getJSONObject(i);
                        arrayListServeur.add(jsonServeur.getString("NOM"));
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("test",e.getMessage());
                }
                Spinner spinnerServeur = findViewById(R.id.serveur);

                ArrayAdapter<String> arrayAdapterServeur = new ArrayAdapter<String>(AjoutAffectation.this, android.R.layout.simple_list_item_1, arrayListServeur);

                runOnUiThread(() -> {
                    spinnerServeur.setAdapter(arrayAdapterServeur);

                });

            JSONArray finalJsonArrayServeur = jsonArrayServeur;
            spinnerServeur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    try {
                        serveur = (JSONObject) finalJsonArrayServeur.getJSONObject(i);
                        Log.d("testGa", serveur.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("testG", e.getMessage());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }


    public void onFailure(Call call, IOException e) {
        Log.d("Test", "erreur!!! connexion impossible");
        Log.d("Test", e.getMessage());
    }

});
}

    public void importListTables() throws IOException{
        ArrayList arrayListTables = new ArrayList<String>();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("REQUEST_METHOD","POST")
                .add("demande","lesTables")
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/apjy2/Amphityon/back_end/api/table.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call,@NonNull Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayTables = null;
                Log.d("test",responseStr);
                try {
                    jsonArrayTables = new JSONArray(responseStr);
                    for (int i = 0; i < jsonArrayTables.length(); i++) {
                        JSONObject jsonTable;
                        jsonTable = jsonArrayTables.getJSONObject(i);
                        arrayListTables.add(jsonTable.getString("NOM"));
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("test",e.getMessage());
                }
                Spinner spinnerServeur = findViewById(R.id.serveur);

                ArrayAdapter<String> arrayAdapterServeur = new ArrayAdapter<String>(AjoutAffectation.this, android.R.layout.simple_list_item_1, arrayListTables);

                runOnUiThread(() -> {
                    spinnerServeur.setAdapter(arrayAdapterServeur);

                });

                JSONArray finalJsonArrayTables = jsonArrayTables;
                spinnerServeur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        try {
                            serveur = (JSONObject) finalJsonArrayTables.getJSONObject(i);
                            Log.d("testGa", serveur.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("testG", e.getMessage());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }


            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
                Log.d("Test", e.getMessage());
            }

        });
    }
    public void ajoutAffect() throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();
        int idService = 0;
        idService = getIntent().getIntExtra("idService", idService);

        String strIdServeur = null;

        try {
            strIdServeur = serveur.getString("IDUSER");
            Log.d("testGB", strIdServeur);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String idUser = user.getString("IDUSER");

        RequestBody formBody = null;

        formBody = new FormBody.Builder()
                .add("RequestMethod", "POST")
                .add("demande", "affecter")
                .add("idUser", strIdServeur)
                .add("idService", String.valueOf(idService))
                .add("idUser1", idUser)
                .add("numTable", editTextTable.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/apjy2/Amphityon/back_end/api/affectation.php")
                .post(formBody)
                .build();


        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("Test", "Reponse : " + responseStr);
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }

        });
    }


}
