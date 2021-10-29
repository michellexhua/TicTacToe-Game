package com.example.tictactoeproject;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.os.AsyncTask;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener{

    Button goButton, addButton;
    EditText inputName;
    Spinner spinner;
    ProgressBar progressBar;
    ArrayList<String> nameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        addButton = findViewById(R.id.btn_add);
        inputName = findViewById(R.id.input_name);
        progressBar = findViewById(R.id.progress_bar);

        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();
        nameList.add("Michelle");
        nameList.add("Hannah");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();

                if(name.trim().length() > 0)
                {
                    nameList.add(inputName.getText().toString());
                    inputName.setText("");

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputName.getWindowToken(),0 );
                    loadSpinnerData();
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter Username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void startAsynTask(View v){
        ExampleAsyncTask task = new ExampleAsyncTask(this);
        task.execute(3);
    }

    private class ExampleAsyncTask extends AsyncTask<Integer, Integer, String> {
        private WeakReference<MainActivity> activityWeakReference;

        ExampleAsyncTask(MainActivity activity) {
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                publishProgress((i * 100) / integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Finished!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            startActivity(new Intent(MainActivity.this, Home.class));
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void loadSpinnerData(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getNameData());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String name = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "You selected: " + name, Toast.LENGTH_LONG).show();
        EasyGame.setUsername(name);
        HardGame.setUsername(name);
        TwoPlayerGame.setUsername(name);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0){
    }

    public List<String> getNameData(){
        List<String> nameData = nameList;
        return nameData;
    }

}