package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class ConfigActivity extends ActionBarActivity {

    EditText distancia;
    Spinner tarjeta;
    CheckBox servicio;

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        distancia = (EditText)findViewById(R.id.distancia);
        tarjeta = (Spinner)findViewById(R.id.tarjeta);
        servicio = (CheckBox)findViewById(R.id.servicio);

        settings = getSharedPreferences("UserInfo", 0);
        distancia.setText(String.valueOf(settings.getInt("distancia", 200)));
        tarjeta.setSelection(settings.getInt("tarjeta", 0));
        servicio.setChecked(settings.getBoolean("servicio", true));
    }

    public void cancelar(View view) {
        finish();
    }

    public void guardar(View view) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("distancia", Integer.parseInt(distancia.getText().toString()));
        editor.putInt("tarjeta", tarjeta.getSelectedItemPosition());
        editor.putBoolean("servicio", servicio.isChecked());
        editor.commit();

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config, menu);
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
