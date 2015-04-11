package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class DetalleActivity extends ActionBarActivity {

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

//        ListView mPager = (ListView) findViewById(R.id.gallery);
//        // GalleryPageAdapter mPagerAdapter = new GalleryPageAdapter(getSupportFragmentManager(), beneficio.getImagenes());
//        mPager.setAdapter(new GalleryItemAdapter(this, beneficio.getImagenes()));
//        mPager.setOnTouchListener(new View.OnTouchListener() {
//            // Setting on Touch Listener for handling the touch inside ScrollView
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // Disallow the touch request for parent scroll on touch of child view
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
//        // setListViewHeightBasedOnChildren(mPager);

//        final ArrayList<String> imgs = beneficio.getImagenes();
//        LinearLayout layoutImgs = (LinearLayout)findViewById(R.id.layoutImgs);
//        for (int j = 0; j < imgs.size(); j++) {
//            ImageView img = new ImageView(getApplicationContext());
//            final Bitmap[] bmps = new Bitmap[1];
//            final int finalI = j;
//            AsyncTask task = new AsyncTask() {
//                @Override
//                protected Object doInBackground(Object[] params) {
//                    try {
//                        InputStream in = new URL(imgs.get(finalI)).openStream();
//                        bmps[0] = BitmapFactory.decodeStream(in);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    return null;
//                }
//            };
//            task.execute();
//            try {
//                task.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//
//            Bitmap bmp = bmps[0];
//
//            img.setImageBitmap(bmp);
//            img.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT));
//
//            layoutImgs.addView(img);
//        }

//        Gallery g = (Gallery)findViewById(R.id.gallery);
//        g.setAdapter(new BaseAdapter() {
//        });

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
        return super.onOptionsItemSelected(item);
    }
}
