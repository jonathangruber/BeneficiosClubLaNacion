package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;


public class DetalleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Intent i = getIntent();
        String codigo =  i.getStringExtra("beneficio");
        Beneficio beneficio = null;
        DatosBeneficio datosBeneficio = null;
        Establecimiento establecimiento = null;

        try {
            beneficio = BeneficiosWebService.obtenerPorId(codigo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ViewPager mPager = (ViewPager) findViewById(R.id.gallery);
        GalleryPageAdapter mPagerAdapter = new GalleryPageAdapter(getSupportFragmentManager(), beneficio.getImagenes());
        mPager.setAdapter(mPagerAdapter);

        establecimiento = beneficio.getEstablecimiento();
        datosBeneficio = beneficio.getDatosBeneficio();

        ((TextView)findViewById(R.id.lugar)).setText(establecimiento.getNombre());
        ((TextView)findViewById(R.id.direccion)).setText(establecimiento.getDireccion());
        ((TextView)findViewById(R.id.tipo)).setText(datosBeneficio.getTipo());
        ((TextView)findViewById(R.id.descripcion)).setText(datosBeneficio.getDescripcion());

        GoogleMap map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        LatLng latlong = new LatLng(beneficio.getPoint().getLatitude(), beneficio.getPoint().getLongitude());
        map.addMarker(new MarkerOptions()
            .title(establecimiento.getNombre())
            .snippet(establecimiento.getDireccion())
            .position(latlong));

        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(latlong, 12F);
        map.moveCamera(cu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
