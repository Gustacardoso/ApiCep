package com.example.gustavo.com.apicep;

import androidx.appcompat.app.AppCompatActivity;

import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button botaorecuperar;
    private EditText editText;
    private TextView textoresultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaorecuperar = findViewById(R.id.buttonRecuperar);
        textoresultado = findViewById(R.id.textResultado);
        editText = findViewById(R.id.idcep);

        botaorecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTask task = new MyTask();
                String cep = editText.getText().toString();
                String urlCep= "https://viacep.com.br/ws/" + cep + "/json/";
                task.execute(urlCep);

            }
        });
    }

    private class MyTask extends AsyncTask<String, Void , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {
                URL url = new URL( stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                //Recupera os dados em Bytes
                inputStream = conexao.getInputStream();

                //inputStreamReader os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //objeto utilizado para leitura dos caracteres do inputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";
                //nesse momento  a string esta sendo  locada em linhas,
                //enquando ela for diferente de null, ela ira execultar
                while ((linha = reader.readLine()) !=  null){
                      buffer.append(linha);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            textoresultado.setText(resultado);
        }
    }
}
