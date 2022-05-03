package com.example.amphy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


public class MainActivity extends AppCompatActivity {
    String responseStr;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonValiderAuthentification = (Button)findViewById(R.id.buttonValiderAuthentification);
        buttonValiderAuthentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appel de la fonction authentification
                try {
                    authentification();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        final Button buttonQuitterAuthentification = (Button)findViewById(R.id.buttonQuitterAuthentification);
        buttonQuitterAuthentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
    }



    public void authentification() throws IOException{
        final EditText textLogin = findViewById(R.id.editTextLogin);
        final EditText textMdp = findViewById(R.id.editTextMdp);

        RequestBody formBody = new FormBody.Builder()
                .add("login", textLogin.getText().toString())
                .add("mdp",textMdp.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.56.1/Amphityon/back_end/api/login.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse( Call call, Response response) throws IOException {
                responseStr = response.body().string();
                Log.d("Test", responseStr);
                if(responseStr.compareTo("false")!=0){

                    try{
                        JSONObject user = new JSONObject(responseStr);
                        Log.d("Test",user.getString("NOM") + " est  connecté");
                        Log.d("AZDJOAZDJZDADAZDKLAZD", user.getString("FONCTION"));
                        if(user.getString("FONCTION").equals("SALLE")) {
                            Intent intent = new Intent(MainActivity.this, MenuChefSalle.class);
                            intent.putExtra("user", user.toString());
                            startActivity(intent);
                        }
                        else if(user.getString("FONCTION").equals("CUISINE")) {
                            Intent intent = new Intent(MainActivity.this, MenuChefCuisine.class);
                            intent.putExtra("user", user.toString());
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(MainActivity.this, "Erreur de connexion !!!! !", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.d("Test","Login ou mot de  passe non valide !");
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Test","erreur!!! connexion impossible");
            }
        });
    }


}