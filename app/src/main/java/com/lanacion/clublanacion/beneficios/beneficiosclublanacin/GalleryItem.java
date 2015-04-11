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
 * Created by Diego on 11/04/2015.
 */
class GalleryItemAdapter extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;
    ArrayList<String> data = null;

    public GalleryItemAdapter(Context context, ArrayList<String> data) {
        super(context, R.layout.fragment_gallery_image, data);
        this.context = context;
        this.layoutResourceId = R.layout.fragment_gallery_image;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ImageView img;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            img = (ImageView)row.findViewById(R.id.img);

            row.setTag(img);
        }
        else
        {
            img = (ImageView)row.getTag();
        }

        final String str = data.get(position);

        final Bitmap[] bmp = new Bitmap[1];
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    InputStream in = new URL(str).openStream();
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
        img.setImageBitmap(bmp[0]);

        return row;
    }
}
