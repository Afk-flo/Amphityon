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
import java.util.Stack;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlatUnique extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plat_unique);

        JSONObject plat;
        try {
            plat = new JSONObject(getIntent().getStringExtra("plat"));
            System.out.println(plat);
            Log.d("PLATTTTTTTTTTTTTT", plat.toString());

            // On affiche les infos
            EditText nom = (EditText) this.findViewById(R.id.NomEdition);
            nom.setText(plat.getString("NOMPLAT"));

            EditText descriptio= (EditText) this.findViewById(R.id.DescriptionEdition);
            descriptio.setText(plat.getString("DESCRIPTIF"));

            // Btn de suppression
            final Button buttonDelete = findViewById(R.id.buttonSuppression);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        suppression(plat.getInt("IDPLAT"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(PlatUnique.this, Plats.class);
                    startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void suppression(int id) {
        String responseStr;
        OkHttpClient client = new OkHttpClient();

        ArrayList<String> arrayListePlats = new ArrayList<String>();

        RequestBody formBody = new FormBody.Builder()
                .add("Request_METHOD", "DELETE")
                .add("id", String.valueOf(id))
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
                Intent intent = new Intent(PlatUnique.this, Plats.class);
                startActivity(intent);
            }
        });
    }


}