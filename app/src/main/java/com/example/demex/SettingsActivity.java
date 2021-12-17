package com.example.demex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SettingsActivity extends AppCompatActivity {

    private int token;
    public User user;
    private TextView userNameText, emailText, cashText, driveTimeText, exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userNameText = findViewById(R.id.user_login);
        cashText = findViewById(R.id.user_cash);
        driveTimeText = findViewById(R.id.user_time_drive);
        emailText = findViewById(R.id.user_email);
        exitBtn = findViewById(R.id.exit_button);

        if (getIntent().hasExtra("token")) {
            token = getIntent().getIntExtra("token", 0);
            getProfileInfo(token);
        }
    }

        public void getProfileInfo(int token) {
        OkHttpClient client = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("token", String.valueOf(token)).build();

        Request request = new Request.Builder()
                .post(formBody)
                .url("http://cars.areas.su/profile").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.code() == 200) {
                    try {
                        JSONObject jsonData = new JSONObject(response.body().string());
                        String username = jsonData.getString("username");
                        String firstname = jsonData.getString("firstname");
                        String secondname = jsonData.getString("secondname");
                        String email = jsonData.getString("email");
                        int timeDrive = jsonData.getInt("timeDrive");
                        int cash = jsonData.getInt("cash");

                        user = User.getInstance(username, firstname, secondname, email, timeDrive, cash, token);

                        runOnUiThread( () -> {
                            userNameText.setText(user.getUsername());
                            cashText.setText(user.getCash() + " $");
                            driveTimeText.setText(user.getTimeDrive() + " hours");
                            emailText.setText(user.getEmail());

                            exitBtn.setOnClickListener(v -> {
                                OkHttpClient client = new OkHttpClient();

                                FormBody formBody = new FormBody.Builder()
                                        .add("username", user.getUsername()).build();

                                Request request = new Request.Builder()
                                        .post(formBody)
                                        .url("http://cars.areas.su/logout").build();

                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        if (response.code() == 200) {
                                            user = null;
                                            System.gc();
                                            System.out.println(user);
                                            startActivity(new Intent(getApplicationContext(), Authorization.class));
                                            finish();
                                        }
                                    }
                                });
                            });
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}