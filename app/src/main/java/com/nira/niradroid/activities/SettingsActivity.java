package com.nira.niradroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nira.niradroid.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private TextView themeTV;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Settings");

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor myEdit = sharedPreferences.edit();

        RadioGroup radioGroup = findViewById(R.id.idRGgroup);
        themeTV = findViewById(R.id.idtvTheme);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // on radio button check change
            switch (checkedId) {
                case R.id.idRBLight:
                    themeTV.setText("Light Theme");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    myEdit.putString("type", "light");
                    myEdit.apply();
                    break;
                case R.id.idRBDark:
                    themeTV.setText("Dark Theme");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    myEdit.putString("type", "dark");
                    myEdit.apply();
                    break;
                case R.id.idRBDefault:
                    themeTV.setText("System Default");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    myEdit.putString("type", "default");
                    myEdit.apply();
                    break;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}