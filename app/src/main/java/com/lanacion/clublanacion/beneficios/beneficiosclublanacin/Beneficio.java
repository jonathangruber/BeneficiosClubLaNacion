package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Diego on 10/04/2015.
 */
public class Beneficio {

    private String id;
    private Location point;
    private DatosBeneficio datosBeneficio;
    private Establecimiento establecimiento;
    private String imagen;
    private Date desde;
    private Date hasta;

    public static Beneficio Parse(JSONObject object) throws JSONException {
        Beneficio b = new Beneficio();

        b.id = object.getString("id");
        b.point = buildLocation(object.getJSONArray("point"));
        b.datosBeneficio = DatosBeneficio.Parse(object.getJSONObject("beneficio"));
        b.establecimiento = Establecimiento.Parse(object.getJSONObject("establecimiento"));
        b.imagen = object.getString("imagen");
        b.desde = getDate(object.getString("desde"));
        b.hasta = getDate(object.getString("hasta"));
        return b;
    }

    public ArrayList<String> getImagenes() {
        String[] imagenes = this.imagen.split("-");
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < imagenes.length; i++) {
            String[] info = imagenes[i].split(":");
            result.add("http://club.lanacion.com.ar/imagenes/".concat(info[0].split("=")[1]));
        }
        return result;
    }

    private static Date getDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Location buildLocation(JSONArray point) throws JSONException {
        Location l = new Location("");
        l.setLatitude(point.getDouble(0));
        l.setLongitude(point.getDouble(1));
        return l;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DatosBeneficio getDatosBeneficio() {
        return datosBeneficio;
    }

    public void setDatosBeneficio(DatosBeneficio datosBeneficio) {
        this.datosBeneficio = datosBeneficio;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimineto) {
        this.establecimiento = establecimineto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Location getPoint() {
        return point;
    }

    public void setPoint(Location point) {
        this.point = point;
    }

    public String getUrl() {
        return "http://club.lanacion.com.ar/beneficio?id=".concat(String.valueOf(this.establecimiento.getId()));
    }
}
