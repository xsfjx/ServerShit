package com.servershit.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.servershit.R;
import com.servershit.tools.MyApplication;
import com.servershit.utils.SharedPref;

public class SharedPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        final EditText etName = findViewById(R.id.etName);
        final EditText etAge = findViewById(R.id.etAge);
        final Switch theQ = findViewById(R.id.theQ);
        final Context context = MyApplication.context;
        Button setBtn = findViewById(R.id.setBtn);
        Button getBtn = findViewById(R.id.getBtn);

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
                Toast.makeText(context, "F name is : " + SharedPref.getFName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Age is : " + SharedPref.getAge(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Single? " + SharedPref.isSingle(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
