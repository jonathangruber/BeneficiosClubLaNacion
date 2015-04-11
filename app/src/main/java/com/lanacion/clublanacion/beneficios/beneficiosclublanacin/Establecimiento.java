package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Diego on 10/04/2015.
 */
public class Establecimiento {
    private int id;
    private String sucursal;
    private String nombre;
    private String detalle;
    private String descripcion;
    private String direccion;

    public static Establecimiento Parse(JSONObject object) throws JSONException {
        Establecimiento e = new Establecimiento();

        e.id = object.getInt("id");
        e.sucursal = object.getString("sucursal");
        e.nombre = object.getString("nombre");
        if (object.has("detalle"))
            e.detalle = object.getString("detalle");
        e.direccion = object.getString("direccion");
        return e;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
