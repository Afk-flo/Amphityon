package com.example.amphy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MenuChefCuisine extends AppCompatActivity {
    String responseStr;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chefcuisine);

        String user;
        Bundle extra = getIntent().getExtras();
        user = extra.getString("user");

        final Button buttonPlats = findViewById(R.id.btnPlats);
        Log.d("test", (String) buttonPlats.getText());
        buttonPlats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fonction de récupération d'info


                    Request request = new Request.Builder()
                            .url("http://192.168.56.1/Amphityon/back_end/api/cuisine.php")
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
                            responseStr = response.body().string();
                            Log.d("E1 - Good", responseStr);

                            if (responseStr.compareTo("false") != 0) {
                                try {
                                    JSONArray plats = new JSONArray(responseStr);

                                    // Convertira ArrayJSON en ArrayList
                                    ArrayList<String> listdata = new ArrayList<String>();

                                    for(int i=0; i < plats.length() ; i++) {
                                        JSONObject json_data = plats.getJSONObject(i);
                                        int id=json_data.getInt("IDPLAT");
                                        String name=json_data.getString("NOMPLAT");
                                        listdata.add(name);
                                        Log.d(name,"Output");
                                    }


                                    Intent intent = new Intent(MenuChefCuisine.this, Plats.class);
                                    intent.putExtra("user", user.toString());
                                    intent.putExtra("plats", listdata);
                                    Bundle b = new Bundle();
                                    b.putString("Array",plats.toString());
                                    intent.putExtras(b);
                                    startActivity(intent);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
            }
        });


        final Button buttonService = (Button)findViewById(R.id.btnService);
        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuChefCuisine.this, Service.class);
                intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });

        final Button buttonQtt = (Button)findViewById(R.id.btnQtt);
        buttonQtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuChefCuisine.this, Plats.class);
                intent.putExtra("user", user.toString());
                startActivity(intent);
            }
        });

        }
    }
