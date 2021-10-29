package com.example.tictactoeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TwoPlayerGame extends AppCompatActivity implements View.OnClickListener{

    public TextView playerOneScore, playerTwoScore, playerStatus, playerWinner, playerOneName;
    public Button[] buttons = new Button[9];
    public Button resetGame;
    public static String username;

    public int playerOneScoreCount, playerTwoScoreCount, roundCount;
    boolean activePlayer;
    boolean gameActive = true;

    private static final String KEY_BTN0 = "zero_key";
    private static final String KEY_BTN1 = "one_key";
    private static final String KEY_BTN2 = "two_key";
    private static final String KEY_BTN3 = "three_key";
    private static final String KEY_BTN4 = "four_key";
    private static final String KEY_BTN5 = "five_key";
    private static final String KEY_BTN6 = "six_key";
    private static final String KEY_BTN7 = "seven_key";
    private static final String KEY_BTN8 = "eight_key";
    private static final String KEY_POINTS1 = "points1_key";
    private static final String KEY_POINTS2 = "points2_key";

    //p1 -> 0
    //p2 -> 1
    //empty -> 2
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //rows
            {0,3,6}, {1,4,7}, {2,5,8}, //columns
            {0,4,8}, {2,4,6} //diagonals
    };

    public static String getUsername(){ return EasyGame.username; }
    public static void setUsername(String username){ EasyGame.username = username; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_game);

        playerOneScore = (TextView) findViewById(R.id.player1_score);
        playerTwoScore = (TextView) findViewById(R.id.player2_score);
        playerStatus = (TextView) findViewById(R.id.update_text);
        playerWinner = (TextView) findViewById(R.id.winner_text);
        playerOneName = (TextView) findViewById(R.id.player1);

        resetGame = (Button) findViewById(R.id.reset_btn);
        playerOneName.setText(getUsername());
        playerStatus.setText(getUsername() + " Turn");

        for(int i = 0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
        resetGame.setVisibility(View.INVISIBLE);
        playerWinner.setVisibility(View.INVISIBLE);

        if(savedInstanceState != null) {
            String savedBtn0 = savedInstanceState.getString(KEY_BTN0);
            buttons[0].setText(savedBtn0);
            String savedBtn1 = savedInstanceState.getString(KEY_BTN1);
            buttons[1].setText(savedBtn1);
            String savedBtn2 = savedInstanceState.getString(KEY_BTN2);
            buttons[2].setText(savedBtn2);
            String savedBtn3 = savedInstanceState.getString(KEY_BTN3);
            buttons[3].setText(savedBtn3);
            String savedBtn4 = savedInstanceState.getString(KEY_BTN4);
            buttons[4].setText(savedBtn4);
            String savedBtn5 = savedInstanceState.getString(KEY_BTN5);
            buttons[5].setText(savedBtn5);
            String savedBtn6 = savedInstanceState.getString(KEY_BTN6);
            buttons[6].setText(savedBtn6);
            String savedBtn7 = savedInstanceState.getString(KEY_BTN7);
            buttons[7].setText(savedBtn7);
            String savedBtn8 = savedInstanceState.getString(KEY_BTN8);
            buttons[8].setText(savedBtn8);
            int savedPoints1 = savedInstanceState.getInt("KEY_POINTS1");
            playerOneScore.setText(Integer.toString(savedPoints1));
            int savedPoints2 = savedInstanceState.getInt("KEY_POINTS2");
            playerTwoScore.setText(Integer.toString(savedPoints2));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(KEY_BTN0, buttons[0].getText().toString());
        savedInstanceState.putString(KEY_BTN1, buttons[1].getText().toString());
        savedInstanceState.putString(KEY_BTN2, buttons[2].getText().toString());
        savedInstanceState.putString(KEY_BTN3, buttons[3].getText().toString());
        savedInstanceState.putString(KEY_BTN4, buttons[4].getText().toString());
        savedInstanceState.putString(KEY_BTN5, buttons[5].getText().toString());
        savedInstanceState.putString(KEY_BTN6, buttons[6].getText().toString());
        savedInstanceState.putString(KEY_BTN7, buttons[7].getText().toString());
        savedInstanceState.putString(KEY_BTN8, buttons[8].getText().toString());
        savedInstanceState.putInt("KEY_POINTS1", playerOneScoreCount);
        savedInstanceState.putInt("KEY_POINTS2", playerTwoScoreCount);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(gameActive){
            if(activePlayer){
                ((Button) v).setText("X");
                ((Button) v).setTextColor(Color.parseColor("#FFFFFFFF"));
                gameState[gameStatePointer] = 0;
            }else{
                ((Button) v).setText("O");
                ((Button) v).setTextColor(Color.parseColor("#FF000000"));
                gameState[gameStatePointer] = 1;
            }
            roundCount++;
        }

        if(checkWinner() && gameActive){
            if(activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                gameActive = false;
                playerStatus.setVisibility(View.INVISIBLE);
                playerWinner.setText(getUsername() + " Won!");
                playerWinner.setVisibility(View.VISIBLE);
                resetGame.setVisibility(View.VISIBLE);
            }else{
                playerTwoScoreCount++;
                updatePlayerScore();
                gameActive = false;
                playerStatus.setVisibility(View.INVISIBLE);
                playerWinner.setText("Player Two Won!");
                playerWinner.setVisibility(View.VISIBLE);
                resetGame.setVisibility(View.VISIBLE);
            }
        }else if(roundCount == 9){
            playerStatus.setVisibility(View.INVISIBLE);
            gameActive = false;
            playerWinner.setText("No Winner!");
            playerWinner.setVisibility(View.VISIBLE);
            resetGame.setVisibility(View.VISIBLE);
        }else{
            activePlayer = !activePlayer;
        }

        if(activePlayer){
            playerStatus.setText(getUsername() + " Turn");
        }else{
            playerStatus.setText("Player Two Turn");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActive = true;
                playerStatus.setVisibility(View.VISIBLE);
                playerWinner.setVisibility(View.INVISIBLE);
                resetGame.setVisibility(View.INVISIBLE);
                roundCount = 0;
                activePlayer = true;

                for(int i = 0; i < buttons.length; i++){
                    gameState[i] = 2;
                    buttons[i].setText("");
                }
            }
        });
    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int[] winningPosition : winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                            gameState[winningPosition[0]] !=2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                startActivity(new Intent(TwoPlayerGame.this, Home.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(TwoPlayerGame.this, Settings.class));
                return true;
            case R.id.scores:
                startActivity(new Intent(TwoPlayerGame.this, Scores.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}