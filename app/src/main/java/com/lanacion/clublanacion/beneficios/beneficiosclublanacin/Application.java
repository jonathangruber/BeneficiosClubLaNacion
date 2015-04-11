package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

/**
 * Created by Diego on 11/04/2015.
 */
public final class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "HelveticaNeueLTPro-MdCn.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "HelveticaNeueLTPro-MdCn.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "HelveticaNeueLTPro-MdCn.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "HelveticaNeueLTPro-MdCn.ttf");
    }
}