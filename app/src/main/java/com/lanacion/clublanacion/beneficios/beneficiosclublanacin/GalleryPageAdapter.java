package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Diego on 10/04/2015.
 */
public class GalleryPageAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> images;

    public GalleryPageAdapter(FragmentManager fm, ArrayList<String> imgs) {
        super(fm);
        this.images = imgs;
    }

    @Override
    public Fragment getItem(int i) {
        //
        ImagePageFragment imgfrag = new ImagePageFragment();
        Bundle b = new Bundle();
        b.putString("img", images.get(i));
        imgfrag.setArguments(b);
        return imgfrag;
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
