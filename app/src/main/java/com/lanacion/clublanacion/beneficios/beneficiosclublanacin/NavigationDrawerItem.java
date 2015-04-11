package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

/**
 * Created by Diego on 11/04/2015.
 */
public class NavigationDrawerItem {
    private String nombre;

    private int imagen;

    public NavigationDrawerItem() {

    }

    public NavigationDrawerItem(String nombre, int imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
