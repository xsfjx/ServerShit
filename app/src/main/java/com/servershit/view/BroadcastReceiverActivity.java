package com.servershit.view;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.servershit.R;
import com.servershit.utils.InternetReceiver;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BroadcastReceiverActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        InternetReceiver receiver = new InternetReceiver();
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

        initReceiver();
        try {
            String res = initOkhttp("https://raw.github.com/square/okhttp/master/README.md");
            Log.i("Log : ", "okHttp response : " + res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initReceiver() {
        InternetReceiver receiver = new InternetReceiver();
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String initOkhttp(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
