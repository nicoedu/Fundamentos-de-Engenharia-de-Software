package com.atividades.ufrpe.solparatodos.gui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atividades.ufrpe.solparatodos.Dominio.Variaveis;
import com.atividades.ufrpe.solparatodos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import zh.wang.android.yweathergetter4a.WeatherInfo;
import zh.wang.android.yweathergetter4a.YahooWeather;
import zh.wang.android.yweathergetter4a.YahooWeatherInfoListener;

public class VariavelActivity extends AppCompatActivity implements YahooWeatherInfoListener {

    private Button botaoVariavel;
    private TextView textoVariavel;
    private YahooWeather mYahooWeather = YahooWeather.getInstance(5000, true);
    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference variavelReferencia = databaseReferencia.child("variavel").child(firebaseAuth.getCurrentUser().getUid());
    private Variaveis variavel;
    private ProgressDialog pd;
    private String array[];
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variavel);
        botaoVariavel = (Button) findViewById(R.id.botaoObterVariavelId);
        textoVariavel = (TextView) findViewById(R.id.tvVariavlID);

        variavelReferencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                variavel = dataSnapshot.getValue(Variaveis.class);
                if (variavel != null) {
                    textoVariavel.setText(variavel.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        botaoVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    textoVariavel.setText("Necessario permissao de GPS!");
                    return;
                }
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                searchByGPS();
                String url = "https://api.solcast.com.au/radiation/forecasts?longitude=" + longitude + "&latitude=" + latitude + "&api_key=zs0u58dlitxgRnq4udFY_KnqMZcrWvkl&format=json";
                new JsonTask().execute(url);

            }
        });

    }

    private void searchByGPS() {
        mYahooWeather.setNeedDownloadIcons(true);
        mYahooWeather.setUnit(YahooWeather.UNIT.CELSIUS);
        mYahooWeather.setSearchMode(YahooWeather.SEARCH_MODE.GPS);
        mYahooWeather.queryYahooWeatherByGPS(getApplicationContext(), this);
    }

    private long radiacaoSolarSemanal() {
        long contador = 0;
        if (array != null) {
            for (String s : array) {
                int valor = s.indexOf("dni");
                if (valor >= 0) {
                    int valor2 = s.indexOf(':', valor) + 1;
                    int valor3 = s.indexOf(',', valor);
                    contador += Long.valueOf(s.substring(valor2, valor3));
                }
            }
        }
        return contador;
    }

    @Override
    public void gotWeatherInfo(WeatherInfo weatherInfo, YahooWeather.ErrorType errorType) {
        variavel = new Variaveis();
        variavel.setCidade(YahooWeather.addressToPlaceName(weatherInfo.getAddress()));
        variavel.setTemperatura(weatherInfo.getCurrentTemp());
        variavel.setHumidade(weatherInfo.getAtmosphereHumidity());
        variavel.setPressao(weatherInfo.getAtmospherePressure());
        variavel.setVelocidadeVento(weatherInfo.getWindSpeed());
        variavel.setRadiacao(radiacaoSolarSemanal());
        variavel.setData(new Date());

        textoVariavel.setText(variavel.toString());
        variavelReferencia.setValue(variavel);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {


    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(VariavelActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            array = result.split("PT30M");
        }
    }
}