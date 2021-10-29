package com.example.tictactoeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    MediaPlayer musicPlayer;
    Button on_btn, off_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        on_btn = (Button) findViewById(R.id.on_btn);
        off_btn = (Button) findViewById(R.id.off_btn);

        musicPlayer = MediaPlayer.create(this, R.raw.song);

        on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.start();
            }
        });

        off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.pause();
                musicPlayer.seekTo(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                startActivity(new Intent(Settings.this, Home.class));
                return true;
            case R.id.settings:
                displayToast();
                return true;
            case R.id.scores:
                startActivity(new Intent(Settings.this, Scores.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void displayToast() {
        Toast.makeText(this, "Already viewing settings!", Toast.LENGTH_SHORT).show();
    }
}