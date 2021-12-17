package com.example.demex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private int token;
    private ImageButton burgerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("token")) {
            token = getIntent().getIntExtra("token", 0);
        }

        burgerMenu = findViewById(R.id.burger_menu);

        burgerMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
            finish();
        });
    }
}