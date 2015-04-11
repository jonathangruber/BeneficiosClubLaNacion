package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.ArrayList;


public class DetalleActivity extends ActionBarActivity {

    // private ShareActionProvider mShareActionProvider;

    private String shareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        final Intent i = getIntent();
        String codigo =  i.getStringExtra("beneficio");
        Beneficio beneficio = null;
        DatosBeneficio datosBeneficio = null;
        Establecimiento establecimiento = null;

        try {
            beneficio = BeneficiosWebService.obtenerPorId(codigo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<ImageItem> data = new ArrayList<ImageItem>();
        final ArrayList<String> imgs = beneficio.getImagenes();
        for (int h = 0; h < imgs.size(); h++)
            data.add(new ImageItem(imgs.get(h)));
        GridView gridView = (GridView) findViewById(R.id.gridView);
        GridViewAdapter gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, data);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                String url = imgs.get(position);

                                                // todo: mostrar en pantalla.
                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                                startActivity(browserIntent);
                                            }
                                        });


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

        ((ImageView)findViewById(R.id.imgLogo)).setImageResource(R.mipmap.logo);

        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(latlong, 15F);
        map.moveCamera(cu);

        shareUrl = beneficio.getUrl();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle, menu);
Log.i("MyApp", "0");
//        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("MyApp", "1");
                Intent shareIntent = new Intent();
                Log.i("MyApp", "2");
                shareIntent.setAction(Intent.ACTION_SEND);
                Log.i("MyApp", "3");
                shareIntent.setType("text/plain");
                Log.i("MyApp", "4");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                Log.i("MyApp", "5");
                startActivity(Intent.createChooser(shareIntent, "Compartir"));
                Log.i("MyApp", "6");
                return true;
            }
        });

//Log.i("MyApp", "2");
//        // Fetch and store ShareActionProvider
//        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
//Log.i("MyApp", "3");
        return true;
    }

//    private void setShareIntent(Intent shareIntent) {
//        if (mShareActionProvider != null) {
//            mShareActionProvider.setShareIntent(shareIntent);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
}
