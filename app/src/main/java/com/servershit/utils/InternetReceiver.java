package com.servershit.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class InternetReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        try {
            Toast.makeText(context, "onReceive: Oh shit", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
