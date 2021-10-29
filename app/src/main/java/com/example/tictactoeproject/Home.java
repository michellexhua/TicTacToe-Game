package com.example.tictactoeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Set;

public class Home extends AppCompatActivity {

    Button easyBtn, hardBtn, twoPlayerBtn, scoresBtn, settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        easyBtn = (Button)findViewById(R.id.btn_easy);
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, EasyGame.class));
            }
        });

        hardBtn = (Button)findViewById(R.id.btn_hard);
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, HardGame.class));
            }
        });

        twoPlayerBtn = (Button)findViewById(R.id.btn_twoPlayer);
        twoPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, TwoPlayerGame.class));
            }
        });

        scoresBtn = (Button)findViewById(R.id.btn_scores);
        scoresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Scores.class));
            }
        });

        settingsBtn = (Button)findViewById(R.id.btn_settings);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Settings.class));
            }
        });
    }
}