package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Diego on 10/04/2015.
 */
class GpsManager {

    private static boolean started = false;

    private static ArrayList<Observer> observers = new ArrayList<Observer>();

    private static Location ubicacion;

    public GpsManager(Context contexto, Observer o) {
        if (!started)
            Start(contexto);
        observers.add(o);
    }

    public static Location getUbicacion() {
        return ubicacion;
    }

    private static void setUbicacion(Location l) {
        ubicacion = l;
        for (int i = 0; i < observers.size(); i++)
            observers.get(i).update(null, l);
    }

    private static void Start(Context contexto) {
        started = true;
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager)contexto.getSystemService(contexto.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                setUbicacion(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
}
