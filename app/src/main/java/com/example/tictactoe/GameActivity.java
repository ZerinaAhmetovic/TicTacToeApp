package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {
    private TicTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button playAgainBtn = (Button) findViewById(R.id.playAgain_btn);
        Button homeBtn = (Button) findViewById(R.id.home_btn);
        TextView playerTurnView = (TextView) findViewById(R.id.playerTurnView);
        //grab names from NamesActivity
        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");

        homeBtn.setVisibility(View.GONE);
        playAgainBtn.setVisibility(View.GONE);
        //they will appear when there is a winner

        //issue for not entered p1 and p2:
        if(playerNames[0].equals(""))
            playerNames[0]="Player 1";
        if(playerNames[1].equals(""))
            playerNames[1]="Player 2";
        //if (playerNames != null)
            playerTurnView.setText(playerNames[0] + "'s turn");

        ticTacToeBoard = findViewById(R.id.ticTacToeBoard);
        //pass in all those values to setUp
        ticTacToeBoard.setUpGame(homeBtn, playAgainBtn, playerTurnView, playerNames);
    }

    public void onClickPlayAgain(View view) {
        ticTacToeBoard.resetBoard();
        ticTacToeBoard.invalidate();
    }

    public void onClickHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}