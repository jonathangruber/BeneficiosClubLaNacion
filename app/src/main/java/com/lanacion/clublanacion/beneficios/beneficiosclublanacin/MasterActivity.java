package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class MasterActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, Observer {

    ProgressDialog progress;

    Location location;

    boolean creating = true;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.LEFT);

        boolean running = false;
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MainService.class.getName().equals(service.service.getClassName())) {
                running = true;
            }
        }
        if (!running) {
            Intent serviceIntent = new Intent(this, MainService.class);
            startService(serviceIntent);
        }

        new GpsManager(this, this);

        progress = new ProgressDialog(this);
        progress.setTitle("Cargando");
        progress.setMessage("Buscando beneficios cercanos...");
        progress.show();
    }

































    public void forzar(View view) throws JSONException {
        location = new Location("");
        location.setLatitude(-34.5332422);
        location.setLongitude(-58.4672752);
        cargarLista();
    }
























    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        if(creating)
        {
            creating = false;
            return;
        }

        String url = null;
        switch (number) {
            case 1:
                url = "https://www.facebook.com/club.la.nacion";
                break;
            case 2:
                url = "https://twitter.com/clubln";
                break;
            case 3:
                url = "https://plus.google.com/+clublanacion";
                break;
            case 4:
                Intent intent = new Intent(this, ConfigActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
        if (url != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.master, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_master, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MasterActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        try {
            location = (Location) data;

            cargarLista();

        } catch (Exception e) {
            Log.w("MyApp", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (requestCode == 1)
            try {
                cargarLista();
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    private void cargarLista() throws JSONException {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        ArrayList<Beneficio> beneficios = BeneficiosWebService.obtenerGeolocalizados(this, location, settings.getInt("distancia", 200));
        BeneficioItemAdapter adapter = new BeneficioItemAdapter(this, beneficios, location);
        final ListView listView = (ListView) findViewById(R.id.lstBeneficios);
        listView.setAdapter(adapter);

        if (beneficios.size() == 0) {
            Toast.makeText(this, "No se encontraron beneficios.", Toast.LENGTH_LONG).show();
        }

        final Context self = this;

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                Beneficio itemValue = (Beneficio)listView.getItemAtPosition(position);

                Intent intent = new Intent(self, DetalleActivity.class);
                intent.putExtra("beneficio", itemValue.getId());

                startActivityForResult(intent, 1);
            }

        });

        if (progress != null && progress.isShowing())
            progress.dismiss();
    }

}
