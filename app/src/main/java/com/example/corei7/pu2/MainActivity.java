package com.example.corei7.pu2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView Prueba;
    RecyclerView rv;
    LinearLayoutManager llm;
    String url = "http://www.upt.edu.pe/upt/web/index.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        rv = (RecyclerView)findViewById(R.id.rv);

        llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
*/

        new noticias().execute();


    }

    // contenido AsyncTask
    private class noticias extends AsyncTask<Void, Void, Void> {
        String texto;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                //Elements imagen = document.select("a img");                 //Arreglar obtener imagenes
                Elements titulo = document.select("a span center strong");
                Elements contenido = document.select("a span i");



                JSONObject noticias = new JSONObject();
                for (Element element:titulo){
                    texto = texto + element.text();
                    noticias.put("titulo",element.text());
                }

                for (Element element:contenido){
                    noticias.put("contenido",element.text());
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            Prueba = (TextView)findViewById(R.id.Hola);             //ENVIAR EL RECICLER VIEW
            Prueba.setText(texto);
        }
    }
}
