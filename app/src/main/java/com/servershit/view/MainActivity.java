package com.servershit.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.servershit.R;
import com.servershit.tools.MyApplication;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = MyApplication.context;

        findViewById(R.id.sharedBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context , SharedPreferencesActivity.class));
            }
        });

        findViewById(R.id.noteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context , RecycleViewActivity.class));
            }
        });

        findViewById(R.id.brdcastBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context , BroadcastReceiverActivity.class));
            }
        });

        findViewById(R.id.retroBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context , RetroFitClientActivity.class));
            }
        });
    }
}
