package com.example.demex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Authorization extends AppCompatActivity {

    private Button signIn;
    private TextView createANewAccount;
    private EditText loginText, passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        signIn = findViewById(R.id.sign_in);
        createANewAccount = findViewById(R.id.createNewAccount);
        loginText = findViewById(R.id.login);
        passwordText = findViewById(R.id.password);

        signIn.setOnClickListener(v -> {
            try {
                login(loginText.getText().toString(), passwordText.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        createANewAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, Registration.class));
            finish();
        });
    }

    public void login(String login, String password) {
        if (!login.trim().equals("") && !password.trim().equals("")) {
            OkHttpClient client = new OkHttpClient();

            //Тело запроса для POST запросов
            FormBody formBody = new FormBody.Builder()
                    .add("username", login)
                    .add("password", password).build();

            //Если это GET запрос, то удаляем .post
            Request request = new Request.Builder()
                    .post(formBody)
                    .url("http://cars.areas.su/login").build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.code() == 200) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        try {
                            JSONObject jsonData = new JSONObject(response.body().string());
                            int token = jsonData.getJSONObject("notice").getInt("token");
                            intent.putExtra("token", token);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(this, "Логин и пароль не могут быть пустыми", Toast.LENGTH_SHORT).show();
        }
    }
}