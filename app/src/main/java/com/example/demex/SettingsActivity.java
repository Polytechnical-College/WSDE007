package com.example.demex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SettingsActivity extends AppCompatActivity {

    private int token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getIntent().hasExtra("token")) {
            token = getIntent().getIntExtra("token", 0);

        }
    }

        public void getProfileInfo(int token) {
        OkHttpClient client = new OkHttpClient();

        //Тело запроса для POST запросов
        FormBody formBody = new FormBody.Builder()
                .add("token", String.valueOf(token)).build();

        //Если это GET запрос, то удаляем .post
        Request request = new Request.Builder()
                .post(formBody)
                .url("http://cars.areas.su/profile").build();
    }
}