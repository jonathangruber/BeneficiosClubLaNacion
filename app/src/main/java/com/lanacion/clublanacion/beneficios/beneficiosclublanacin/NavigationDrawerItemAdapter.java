package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
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
public class NavigationDrawerItemAdapter extends ArrayAdapter<NavigationDrawerItem> {
    LayoutInflater inflater;
    int layoutResourceId;
    ArrayList<NavigationDrawerItem> data = null;

    public NavigationDrawerItemAdapter(LayoutInflater inflater, ArrayList<NavigationDrawerItem> data) {
        super(inflater.getContext(), R.layout.navigation_drawer_item_view, data);
        this.inflater = inflater;
        this.layoutResourceId = R.layout.navigation_drawer_item_view;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if(row == null)
        {
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.text = (TextView)row.findViewById(R.id.text);
            holder.imagen = (ImageView)row.findViewById(R.id.imagen);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)row.getTag();
        }

        NavigationDrawerItem item = data.get(position);
        holder.text.setText(item.getNombre());
        holder.imagen.setImageResource(item.getImagen());

        return row;
    }

    static class ViewHolder {
        TextView text;
        ImageView imagen;
    }
}
