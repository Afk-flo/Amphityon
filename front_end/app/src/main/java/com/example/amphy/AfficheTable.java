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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AfficheTable extends AppCompatActivity {


    JSONObject table;
    JSONObject user;


    EditText editTextNbConv;
    EditText editTextDate;
    EditText editTextIdTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_table);

        try{
            table = new JSONObject(getIntent().getStringExtra("uneTable"));

            Log.d("Test" ,table.toString());

            editTextNbConv = findViewById(R.id.nbconvive);
            editTextDate = findViewById(R.id.date);
            editTextIdTable = findViewById(R.id.idTable);


            editTextNbConv.setText(table.getString("NBCONVIVE"));
            editTextDate.setText(table.getString("DATE1"));
            editTextIdTable.setText(table.getString("NUMTABLE"));

            final Button btnQuitter = findViewById(R.id.btnEnregistrer);
            btnQuitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        modifierInfos();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace() ;
                    }
                }
            });


            final Button btnSupp = findViewById(R.id.btnSupprimer);
            btnSupp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        suppTable();
                        Intent intent = new Intent(AfficheTable.this, Menu_Table.class);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace() ;
                    }
                }
            });

        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }


    public void modifierInfos() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        int idService=0;

        idService = getIntent().getIntExtra("idService",idService);


        Log.d("Test",table.toString());

        RequestBody formBody;

        formBody = new FormBody.Builder()
                .add("RequestMethod","POST")
                .add("demande","modifTable")
                .add("numTable", table.getString("NUMTABLE"))
                .add("nbConvive", editTextNbConv.getText().toString())
                .add("idService",String.valueOf(idService))
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

    public void suppTable() throws IOException{

        OkHttpClient client = new OkHttpClient();

        Log.d("Test",table.toString());

        RequestBody formBody = null;

        int idService=0;

        idService = getIntent().getIntExtra("idService",idService);

        String service = String.valueOf(idService);

        try {
            formBody = new FormBody.Builder()
                    .add("RequestMethod","POST")
                    .add("demande","suppTable")
                    .add("numTable", table.getString("NUMTABLE"))
                    .add("idService",service)
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }


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
