package com.servershit.view;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.servershit.R;
import com.servershit.api.Change;
import com.servershit.api.Controller;
import com.servershit.utils.InternetReceiver;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class BroadcastReceiverActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        initReceiver();

        Controller controller = new Controller();
        controller.start();
    }

    private void initReceiver() {
        InternetReceiver receiver = new InternetReceiver();
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    public interface GerritAPI  {
        @GET("changes/")
        Call<List<Change>> loadChanges(@Query("q") String status);
    }
}
