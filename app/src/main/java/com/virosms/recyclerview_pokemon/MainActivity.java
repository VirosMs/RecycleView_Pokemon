package com.virosms.recyclerview_pokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.virosms.recyclerview_pokemon.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivityMainBinding.inflate(getLayoutInflater()).getRoot());
    }
}