package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Diego on 10/04/2015.
 */
public class ImagePageFragment extends Fragment {

    String imageSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_gallery_image, container, false);

        Bundle b = this.getArguments();
        final String img = b.getString("img");
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
        ((ImageView)rootView.findViewById(R.id.img)).setImageBitmap(bmp[0]);

        return rootView;
    }
}
