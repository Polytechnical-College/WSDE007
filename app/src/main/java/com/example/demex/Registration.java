package com.example.demex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Registration extends AppCompatActivity {

    private Button registration;
    private EditText loginText, passwordText, emailText, passwordVerifyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registration = findViewById(R.id.sign_up);
        loginText = findViewById(R.id.login);
        passwordText = findViewById(R.id.password);
        emailText = findViewById(R.id.email);
        passwordVerifyText = findViewById(R.id.password_verify);

        registration.setOnClickListener(v -> {
            signUp(loginText.getText().toString(), emailText.getText().toString(), passwordText.getText().toString(), passwordVerifyText.getText().toString());
        });
    }

    public void signUp(String login, String email, String password, String passwordVerify) {
        if (!login.trim().equals("")
                && !password.trim().equals("")
                && !email.trim().equals("") && !passwordVerify.trim().equals("")) {
            if (password.equals(passwordVerify)) {
                OkHttpClient client = new OkHttpClient();

                //Тело запроса для POST запросов
                FormBody formBody = new FormBody.Builder()
                        .add("username", login)
                        .add("email", email)
                        .add("password", password).build();

                //Если это GET запрос, то удаляем .post
                Request request = new Request.Builder()
                        .post(formBody)
                        .url("http://cars.areas.su/signup").build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.code() == 200) {
                            startActivity(new Intent(getApplicationContext(), Authorization.class));
                            finish();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Пароли не совпадают, проверьте написание!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Все поля обязательны для заполнения", Toast.LENGTH_SHORT).show();
        }
    }
}