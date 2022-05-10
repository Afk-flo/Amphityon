package com.example.amphy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AfficheAffect extends AppCompatActivity {
    JSONObject affect;
    String reponseStr;
    OkHttpClient client = new OkHttpClient();

    EditText editTextNbConv;
    EditText editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_affect);


        try{
            affect = new JSONObject(getIntent().getStringExtra("affect"));

            Log.d("Test" ,affect.toString() );

            editTextNbConv = findViewById(R.id.nbconvive);
            editTextDate = findViewById(R.id.date);

            editTextDate.setText(affect.getString("date") );
            editTextNbConv.setText(affect.getString("nbconvive") );

            try {
                importAffect();
            } catch (IOException e) {
                e.printStackTrace();
            }


            final Button buttonValiderMesInfos = findViewById(R.id.boutonValider);
            buttonValiderMesInfos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        modifierMesInfos();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        catch(JSONException e){

        }
        final Button buttonQuitterMesInfos = (Button)findViewById(R.id.buttonQuitterListe);
        buttonQuitterMesInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfficheAffect.this.finish();
            }
        });

    }

    public void importAffect() throws IOException{
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = null;

        try{
            formBody = new FormBody.Builder()
                    .add("RequestMethod","POST")
                    .add("demande","uneAffect")
                    .add("nbConvive", affect.getString("nbConvive"))
                    .add("service", affect.getString("date"))
                    .build();
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        Request request = new Request.Builder()
                .url("http://192.168.1.94/apjy2/Amphityon/back_end/api//affectation.php")
                .post(formBody)
                .build();
    }


    public void modifierMesInfos() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Log.d("Test",affect.toString());

        RequestBody formBody = null;

        try {
            formBody = new FormBody.Builder()
                    .add("RequestMethod","POST")
                    .add("demande","majTable")
                    .add("idTable", affect.getString("idTable"))
                    .add("nbConvive", affect.getString("nbConvive"))
                    .add("date", affect.getString("date"))
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Request request = new Request.Builder()
                .url("http://192.168.1.94/apjy2/Amphityon/back_end/api/affectation.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("Test","Reponse : "+responseStr);
                if (responseStr.compareTo("false") != 0) {

                } else {

                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }

        });

    }
}