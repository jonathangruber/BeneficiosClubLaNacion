package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Diego on 10/04/2015.
 */
public class DatosBeneficio {
    private int id;
    private String nombre;
    private String categoria;
    private String subcategoria;
    private String descripcion;
    private String tipo;
    private String tarjeta;

    public static DatosBeneficio Parse(JSONObject object) throws JSONException {
        DatosBeneficio db = new DatosBeneficio();

        db.id = object.getInt("id");
        db.nombre = object.getString("nombre");
        db.categoria = object.getString("categoria");
        db.subcategoria = object.getString("subcategoria");
        db.descripcion = object.getString("descripcion");
        db.tipo = object.getString("tipo");
        db.tarjeta = object.getString("tarjeta");
        return db;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
}
