package com.servershit.api;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servershit.view.BroadcastReceiverActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller implements Callback<List<Change>> {

    private static final String BASE_URL = "https://git.eclipse.org/r/";

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BroadcastReceiverActivity.GerritAPI gerritAPI = retrofit.create(BroadcastReceiverActivity.GerritAPI.class);

        Call<List<Change>> call = gerritAPI.loadChanges("status:open");
        call.enqueue(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResponse(Call<List<Change>> call, Response<List<Change>> response) {

        if (response.isSuccessful()) {
            List<Change> changesList = response.body();

            for (Change c : changesList) {
                Log.i("Log2", c.subject);
            }
        } else {
            System.out.println(response.errorBody());
        }

    }

    @Override
    public void onFailure(Call<List<Change>> call, Throwable t) {
        Log.i("Log2", "onFailure: ");
    }
}
