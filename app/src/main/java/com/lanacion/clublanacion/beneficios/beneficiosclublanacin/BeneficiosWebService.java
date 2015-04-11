package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Diego on 10/04/2015.
 */
public class BeneficiosWebService {

    private static final String URL = "http://23.23.128.233:8080/api/";

    private static String armarUrl(String metodo, Object... params) {
        String url =  URL.concat(metodo);
        for (int i = 0; i < params.length; i++)
            url = url.concat("/").concat(String.valueOf(params[i]));
        return url;
    }

    private static String ejectuarGet(String url) {
        final StringBuilder[] stringBuilder = new StringBuilder[1];
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                stringBuilder[0] = new StringBuilder();
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                try {
                    HttpResponse response = httpClient.execute(httpGet);
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode == 200) {
                        HttpEntity entity = response.getEntity();
                        InputStream inputStream = entity.getContent();
                        BufferedReader reader = new BufferedReader( new InputStreamReader(inputStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder[0].append(line);
                        }
                        inputStream.close();
                    } else {
                        Log.d("JSON", "Failed to download file");
                    }
                } catch (Exception e) {
                    Log.d("readJSONFeed", e.getLocalizedMessage());
                }
                return stringBuilder[0].toString();
            }
        };

        try {
            asyncTask.execute(url);
            return asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Beneficio> obtenerGeolocalizados(Context context, Location location, long distancia) throws JSONException {

        String latitud = String.valueOf(location.getLatitude());
        String longitud = String.valueOf(location.getLongitude());
        latitud = latitud.substring(0, 9);
        longitud = longitud.substring(0, 9);
        String url = armarUrl("geo", latitud, longitud, distancia);
        String resultado = ejectuarGet(url);
        JSONArray arr = new JSONArray(resultado);
        ArrayList<Beneficio> result = new ArrayList<Beneficio>();

        long now = System.currentTimeMillis();

        SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        int tarjeta = settings.getInt("tarjeta", 0);
        for (int i = 0; i < arr.length(); i++) {
            Beneficio b = Beneficio.Parse(arr.getJSONObject(i));
            if (b.getDesde().getTime() > now || b.getHasta().getTime() <= now)
                continue;
            if (tarjeta != 0) {
                String tipo = (tarjeta == 1) ? "Classic" : "Premium";
                if (!b.getDatosBeneficio().getTarjeta().contains(tipo))
                    continue;
            }
            result.add(b);
        }
        return result;
    }

    public static Beneficio obtenerPorId(String codigo) throws JSONException {
        String url = armarUrl("beneficio", codigo);
        String resultado = ejectuarGet(url);
        JSONArray arr = new JSONArray(resultado);
        return Beneficio.Parse(arr.getJSONObject(0));
    }
}
