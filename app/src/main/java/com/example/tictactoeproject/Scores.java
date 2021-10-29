package com.example.tictactoeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Scores extends AppCompatActivity {
    TextView scoreTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        scoreTxt = findViewById(R.id.scoreTxt);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        Cursor cursor = db.allScores();
        if(cursor.getCount() == 0){
            scoreTxt.setText("No Data!");
        }else{
            while (cursor.moveToNext()){
                scoreTxt.setText("Name: " + cursor.getString(1) + "\t Score: " + cursor.getString(2) + "\n");
            }
        }
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
                startActivity(new Intent(Scores.this, Home.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(Scores.this, Settings.class));
                return true;
            case R.id.scores:
                displayToast("Already viewing scores!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void displayToast(String message){
        Toast.makeText(Scores.this,message, Toast.LENGTH_SHORT).show();
    }
}