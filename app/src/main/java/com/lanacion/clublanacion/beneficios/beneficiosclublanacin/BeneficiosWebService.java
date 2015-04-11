package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

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
import java.util.ArrayList;
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
        return "[{\"_id\":\"5524091e6d5b9fccf1b9c909\",\"id\":\"383289_111568\",\"point\":[-34.53449,-58.46757],\"imagen\":\"nombre=50106.jpg:Tipo=7:Great=0-nombre=50107.jpg:Tipo=13:Great=1-nombre=50108.jpg:Tipo=13:Great=0-nombre=50109.jpg:Tipo=13:Great=0-nombre=50110.jpg:Tipo=13:Great=0-nombre=50111.jpg:Tipo=13:Great=0\",\"hasta\":\"2015-04-12T12:00:00.000Z\",\"desde\":\"2015-04-10T21:30:00.000Z\",\"establecimiento\":{\"sucursal\":\"111568\",\"nombre\":\"Enogarage\",\"direccion\":\"Av. del Libertador\",\"id\":111567},\"beneficio\":{\"descripcion\":\"¡Sobre el total de tu factura!\",\"subcategoria\":\"Compras Gastronómicas\",\"tarjeta\":\"Classic-Premium\",\"id\":383289,\"nombre\":\"Enogarage\",\"tipo\":\"20%\",\"categoria\":\"Gastronomía\"}},{\"_id\":\"5524091e6d5b9fccf1b9c90d\",\"id\":\"381510_109648\",\"point\":[-34.53258,-58.46859],\"imagen\":\"nombre=44060.jpg:Tipo=2:Great=0\",\"hasta\":\"2015-04-12T23:00:00.000Z\",\"desde\":\"2015-04-11T15:00:00.000Z\",\"establecimiento\":{\"nombre\":\"Mostaza\",\"direccion\":\"Av. del Libertador\",\"id\":109616,\"sucursal\":\"109648\"},\"beneficio\":{\"id\":381510,\"nombre\":\"Mostaza\",\"tipo\":\"20%\",\"categoria\":\"Gastronomía\",\"descripcion\":\"¡Sobre el total de tu factura!\",\"subcategoria\":\"Restaurant\",\"tarjeta\":\"Classic-Premium\"}},{\"_id\":\"5524091e6d5b9fccf1b9c90e\",\"id\":\"381513_109648\",\"point\":[-34.53258,-58.46859],\"imagen\":\"nombre=44265.jpg:Tipo=2:Great=0-nombre=I733649.jpg:Tipo=15:Great=0\",\"hasta\":\"2015-04-13T01:30:00.000Z\",\"desde\":\"2015-04-11T11:30:00.000Z\",\"establecimiento\":{\"sucursal\":\"109648\",\"nombre\":\"Mostaza\",\"direccion\":\"Av. del Libertador\",\"id\":109616},\"beneficio\":{\"subcategoria\":\"Restaurant\",\"tarjeta\":\"Premium\",\"id\":381513,\"nombre\":\"Mostaza\",\"tipo\":\"30%\",\"categoria\":\"Gastronomía\",\"descripcion\":\"¡Sobre el total de tu factura!\"}},{\"_id\":\"5524091e6d5b9fccf1b9c90f\",\"id\":\"384141_112753\",\"point\":[-34.53257,-58.46861],\"imagen\":\"nombre=I735529.jpg:Tipo=13:Great=1-nombre=I735530.jpg:Tipo=13:Great=0-nombre=I735531.jpg:Tipo=13:Great=0-nombre=I735532.jpg:Tipo=13:Great=0-nombre=I737598.jpg:Tipo=7:Great=0\",\"hasta\":\"2015-04-11T10:30:00.000Z\",\"desde\":\"2015-04-11T02:30:00.000Z\",\"establecimiento\":{\"nombre\":\"Pet Ranch\",\"direccion\":\"Av. del Libertador\",\"id\":112752,\"sucursal\":\"112753\"},\"beneficio\":{\"categoria\":\"Más Categorías\",\"descripcion\":\"¡De descuento en alimento balanceado!\",\"subcategoria\":\"Comercios\",\"tarjeta\":\"Classic-Premium\",\"id\":384141,\"nombre\":\"Pet Ranch\",\"tipo\":\"15%\"}},{\"_id\":\"5524091e6d5b9fccf1b9c910\",\"id\":\"384142_112753\",\"point\":[-34.53257,-58.46861],\"imagen\":\"nombre=I735529.jpg:Tipo=13:Great=1-nombre=I735530.jpg:Tipo=13:Great=0-nombre=I735531.jpg:Tipo=13:Great=0-nombre=I735532.jpg:Tipo=13:Great=0-nombre=I737598.jpg:Tipo=7:Great=0\",\"hasta\":\"2015-04-12T11:30:00.000Z\",\"desde\":\"2015-04-11T02:30:00.000Z\",\"establecimiento\":{\"sucursal\":\"112753\",\"nombre\":\"Pet Ranch\",\"direccion\":\"Av. del Libertador\",\"id\":112752},\"beneficio\":{\"tipo\":\"20%\",\"categoria\":\"Más Categorías\",\"descripcion\":\"¡De descuento en accesorios para mascotas!\",\"subcategoria\":\"Comercios\",\"tarjeta\":\"Classic-Premium\",\"id\":384142,\"nombre\":\"Pet Ranch\"}}]";
//        final StringBuilder[] stringBuilder = new StringBuilder[1];
//        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
//            @Override
//            protected String doInBackground(String... params) {
//                stringBuilder[0] = new StringBuilder();
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpGet httpGet = new HttpGet(params[0]);
//                try {
//                    HttpResponse response = httpClient.execute(httpGet);
//                    StatusLine statusLine = response.getStatusLine();
//                    int statusCode = statusLine.getStatusCode();
//                    if (statusCode == 200) {
//                        HttpEntity entity = response.getEntity();
//                        InputStream inputStream = entity.getContent();
//                        BufferedReader reader = new BufferedReader( new InputStreamReader(inputStream));
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            stringBuilder[0].append(line);
//                        }
//                        inputStream.close();
//                    } else {
//                        Log.d("JSON", "Failed to download file");
//                    }
//                } catch (Exception e) {
//                    Log.d("readJSONFeed", e.getLocalizedMessage());
//                }
//                return stringBuilder[0].toString();
//            }
//        };
//
//        try {
//            asyncTask.execute(url);
//            return asyncTask.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    public static ArrayList<Beneficio> obtenerGeolocalizados(Location location, long distancia) throws JSONException {

        String latitud = String.valueOf(location.getLatitude());
        String longitud = String.valueOf(location.getLongitude());
        latitud = latitud.substring(0, 9);
        longitud = longitud.substring(0, 9);
        String url = armarUrl("geo", latitud, longitud, distancia);
        String resultado = ejectuarGet(url);
        JSONArray arr = new JSONArray(resultado);
        ArrayList<Beneficio> result = new ArrayList<Beneficio>();
        for (int i = 0; i < arr.length(); i++)
            result.add(Beneficio.Parse(arr.getJSONObject(i)));
        return result;
    }

    public static Beneficio obtenerPorId(String codigo) throws JSONException {
        String url = armarUrl("beneficio", codigo);
        String resultado = ejectuarGet(url);
        JSONArray arr = new JSONArray(resultado);
        return Beneficio.Parse(arr.getJSONObject(0));
    }
}
