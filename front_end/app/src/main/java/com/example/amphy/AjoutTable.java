package com.example.amphy;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AjoutTable extends AppCompatActivity {
    JSONObject user;
    EditText editTextNbConv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_table);
        editTextNbConv = findViewById(R.id.nbconvive);


        try{
            user = new JSONObject(getIntent().getStringExtra("user"));

            final Button buttonValiderMesInfos = findViewById(R.id.btnEnregistrer);
            buttonValiderMesInfos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ajoutTable();
                        Intent intent = new Intent(AjoutTable.this, Menu_Table.class);
                        startActivity(intent);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace() ;
                    }
                }
            });

            final Button btnQuitter = (Button)findViewById(R.id.btnQuitter);
            btnQuitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AjoutTable.this.finish();
                }
            });
        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }


    public void ajoutTable() throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();
        int idService=0;
        idService = getIntent().getIntExtra("idService",idService);

        String idUser = user.getString("IDUSER");

        RequestBody formBody = null;

        formBody = new FormBody.Builder()
                .add("RequestMethod","POST")
                .add("demande","ajoutTable")
                .add("nbConvive", editTextNbConv.getText().toString())
                .add("idService", String.valueOf(idService))
                .add("idUser",idUser)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.94/apjy2/Amphityon/back_end/api/table.php")
                .post(formBody)
                .build();


        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("Test","Reponse : "+responseStr);
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }

        });
    }

}