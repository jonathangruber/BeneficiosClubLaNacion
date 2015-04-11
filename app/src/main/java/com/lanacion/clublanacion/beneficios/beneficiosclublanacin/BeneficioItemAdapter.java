package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Diego on 10/04/2015.
 */
public class BeneficioItemAdapter extends ArrayAdapter<Beneficio> {
    Context context;
    int layoutResourceId;
    ArrayList<Beneficio> data = null;
    Location currentLocation;

    public BeneficioItemAdapter(Context context, ArrayList<Beneficio> data, Location currentLocation) {
        super(context, R.layout.beneficio_item_view, data);
        this.context = context;
        this.layoutResourceId = R.layout.beneficio_item_view;
        this.data = data;
        this.currentLocation = currentLocation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.lugar = (TextView)row.findViewById(R.id.lugar);
            holder.descuento = (TextView)row.findViewById(R.id.descuento);
            holder.distancia = (TextView)row.findViewById(R.id.distancia);
            holder.imagen = (ImageView)row.findViewById(R.id.imagen);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)row.getTag();
        }

        Beneficio beneficio = data.get(position);
        holder.lugar.setText(beneficio.getEstablecimiento().getNombre());
        holder.descuento.setText(beneficio.getDatosBeneficio().getTipo());
        float distancia = beneficio.getPoint().distanceTo(currentLocation);
        int cuadras = Math.round(distancia / 100);
        holder.distancia.setText(String.valueOf(cuadras).concat(" cuadras"));
        final String img = beneficio.getImagenes().get(0);
        final Bitmap[] bmp = new Bitmap[1];
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    InputStream in = new URL(img).openStream();
                    bmp[0] = BitmapFactory.decodeStream(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute();
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        holder.imagen.setImageBitmap(bmp[0]);

        return row;
    }

    static class ViewHolder {
        TextView lugar;
        TextView descuento;
        TextView distancia;
        ImageView imagen;
    }
}
