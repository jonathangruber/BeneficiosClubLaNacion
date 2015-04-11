package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Diego on 10/04/2015.
 */
public class MainService extends Service implements Observer {
    private boolean isInForegroundMode;

    private ArrayList<String> idsNotificados = new ArrayList<String>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        new GpsManager(this, this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (debeNotificar()) {
            SharedPreferences settings = getSharedPreferences("UserInfo", 0);
            try {
                ArrayList<Beneficio> beneficios = BeneficiosWebService.obtenerGeolocalizados(this, (Location)data, settings.getInt("distancia", 200));

                ArrayList<String> idsNotificadosAnt = new ArrayList<String>(idsNotificados);
                idsNotificados = new ArrayList<String>();
                boolean notificar = false;
                for (int i = 0; i < beneficios.size(); i++) {
                    Beneficio b = beneficios.get(i);
                    idsNotificados.add(b.getId());
                    if (!idsNotificadosAnt.contains(b.getId()))
                        notificar = true;
                }

                if (notificar)
                    notificar();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean debeNotificar() {

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        if (!settings.getBoolean("servicio", true))
            return false;

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager .getRunningTasks(Integer.MAX_VALUE);
        if (services.get(0).topActivity.getPackageName().toString().equalsIgnoreCase(getPackageName().toString()))
            return false;
        return true;
    }

    private void notificar() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha)
                        .setContentTitle("My notification")
                        .setContentText("Hello World from the service!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MasterActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MasterActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(13245, mBuilder.build());
    }
}
