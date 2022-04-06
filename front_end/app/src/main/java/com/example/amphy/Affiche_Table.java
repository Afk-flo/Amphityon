package com.example.amphy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Affiche_Table extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_table);

        try {
            listTables();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void listTables() throws IOException{
        OkHttpClient client = new OkHttpClient();
        ArrayList arrayListNumTables = new ArrayList();

        Request request = new Request.Builder()
                .url("http://192.168.232.233/apjy2/Amphityon/back_end/api/table.php")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String responseStr =response.body().string();
                JSONArray jsonArrayTables = null;
                try{
                    jsonArrayTables = new JSONArray(responseStr);
                    for(int i = 0; i<jsonArrayTables.length(); i++){
                        JSONObject jsonTable =null;
                        jsonTable = jsonArrayTables.getJSONObject(i);
                        arrayListNumTables.add(jsonTable.getInt("numTable"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ListView listViewTables = findViewById(R.id.listViewTables);

                ArrayAdapter arrayAdapterTables = new ArrayAdapter(Affiche_Table.this, android.R.layout.simple_list_item_1, arrayListNumTables);

                listViewTables.setAdapter(arrayAdapterTables);
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Test","erreur !!!!");
            }
        });
    }
}