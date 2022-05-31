package com.example.amphy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Menu_Table extends AppCompatActivity {
    JSONObject user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_table);
<<<<<<< HEAD
        int idService=0;
        idService = getIntent().getIntExtra("idService",idService);
        int finalIdService = idService;

        Button btnAjout = findViewById(R.id.btnAjoutTable);
        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu_Table.this, AjoutTable.class);
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
                Menu_Table.this.finish();
            }
        });


        try {
            Log.d("test","coucou");
            listeTables(idService);
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.d("test",e.getMessage());
        }
=======


>>>>>>> main
    }

    public void listeTables(int unService) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList listTables = new ArrayList<String>();
        RequestBody formBody = new FormBody.Builder()
                .add("REQUEST_METHOD","POST")
                .add("demande","lesTables")
                .add("idService", String.valueOf(unService))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/apjy2/Amphityon/back_end/api/table.php/demande")
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayTables = null;
                Log.d("test",responseStr);
                try {
                    jsonArrayTables = new JSONArray(responseStr);
                    for(int i=0; i<jsonArrayTables.length();i++){
                        JSONObject jsonTable;
                        jsonTable = jsonArrayTables.getJSONObject(i);
                        listTables.add(jsonTable.getString("NUMTABLE"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("test",e.getMessage());
                }

                ListView listViewTables = findViewById(R.id.listViewTables);

                ArrayAdapter<String> arrayAdapterTables = new ArrayAdapter<String>(Menu_Table.this, android.R.layout.simple_list_item_1, listTables);

                runOnUiThread(() -> {
                    listViewTables.setAdapter(arrayAdapterTables);
                });

                JSONArray finalJsonArrayTables = jsonArrayTables;

                listViewTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        try{
                            int idService = 0;
                            idService = getIntent().getIntExtra("idService",idService);
                            JSONObject uneTable = finalJsonArrayTables.getJSONObject(position);
                            Intent intent = new Intent(Menu_Table.this, AfficheTable.class);
                            intent.putExtra("uneTable",uneTable.toString());
                            intent.putExtra("idService",idService);
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
