package com.servershit.view;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.servershit.R;
import com.servershit.utils.InternetReceiver;

public class BroadcastReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        InternetReceiver receiver = new InternetReceiver();
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

    }
}
