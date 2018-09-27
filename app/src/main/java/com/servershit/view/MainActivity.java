package com.servershit.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.servershit.R;
import com.servershit.utils.SharedPref;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etName = findViewById(R.id.etName);
        final EditText etAge = findViewById(R.id.etAge);
        final Switch theQ = findViewById(R.id.theQ);
        Button setBtn = findViewById(R.id.setBtn);
        Button getBtn = findViewById(R.id.getBtn);

        findViewById(R.id.noteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , RecycleViewActivity.class));
            }
        });

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.setFName(etName.getText().toString());
                SharedPref.setAge(Integer.parseInt(etAge.getText().toString()));
                SharedPref.setSingle(theQ.isChecked());
            }
        });

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "F name is : " + SharedPref.getFName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Age is : " + SharedPref.getAge(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Single? " + SharedPref.isSingle(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
