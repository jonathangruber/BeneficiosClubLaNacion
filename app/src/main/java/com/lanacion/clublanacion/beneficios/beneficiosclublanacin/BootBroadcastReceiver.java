package com.lanacion.clublanacion.beneficios.beneficiosclublanacin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Diego on 10/04/2015.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        // BOOT_COMPLETED” start Service
        if (intent.getAction().equals(ACTION)) {
            //Service
            Intent serviceIntent = new Intent(context, MainService.class);
            context.startService(serviceIntent);
        }
    }
}