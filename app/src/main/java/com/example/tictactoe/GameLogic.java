package com.example.tictactoe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {
    private int[][] gameBoard;
    private int player = 1;

    //1st element- row, 2nd column, 3rd winType - 1 2 3 4 hor ver d+ d-
    private int[] winType = {-1, -1, -1};

    private String[] playerNames = {"Player 1", "Player 2"};

    private Button homeBTN;
    private Button playAgainBTN;
    private TextView playerTurn;

    public GameLogic() {
        gameBoard = new int[3][3];

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }

    }

    public boolean updateGameBoard(int row, int col) {
        //if that place empty, update it
        if (gameBoard[row - 1][col - 1] == 0) {
            gameBoard[row - 1][col - 1] = player;

            if (player == 1)
                playerTurn.setText(playerNames[1] + "'s turn");
            else
                playerTurn.setText(playerNames[0] + "'s turn");
            return true;
        } else
            return false;
    }

    public boolean winnerCheck() {
        boolean isWinner = false;
        //horizontal check
        for (int r = 0; r < 3; r++) {
            //PAY ATTENTION! our array is filled with zeros so instantly we will get a match, a 'winner'
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][1] == gameBoard[r][2] && gameBoard[r][0] != 0) {
                winType = new int[]{r, 0, 1};
                isWinner = true;
            }
        }

        //vertical check
        for (int c = 0; c < 3; c++) {
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[1][c] == gameBoard[2][c] && gameBoard[0][c] != 0) {
                winType = new int[]{0, c, 2};
                isWinner = true;
            }
        }

        //diagonal
        //D-
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] && gameBoard[0][0] != 0) {
            winType = new int[] {0,2,3};
            isWinner = true;
        }

        //D+
        if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0] && gameBoard[0][2] != 0) {
            winType = new int[] {2,2,4};
            isWinner = true;
        }

        int boardFilled = 0;
        //go through gameBoard to see if all spaces occupied . game ended
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard[r][c] != 0)
                    boardFilled++;
            }
        }
        //if theres a winner, set btns to visible and display the winner
        if (isWinner) {
            homeBTN.setVisibility(View.VISIBLE);
            playAgainBTN.setVisibility(View.VISIBLE);
            playerTurn.setText(playerNames[player - 1] + " WON !!!");
            return true;
        } else if (boardFilled == 9) {
            homeBTN.setVisibility(View.VISIBLE);
            playAgainBTN.setVisibility(View.VISIBLE);
            playerTurn.setText("It's a tie!");
            return true;
        } else
            return false;

    }

    public void resetGameBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }
        //reset all variables - btns visibility, player turn + update the txtView
        winType=new int[]{-1,-1,-1};
        homeBTN.setVisibility(View.GONE);
        playAgainBTN.setVisibility(View.GONE);
        player = 1;
        playerTurn.setText(playerNames[0] + "'s turn");
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setHomeBTN(Button homeBTN) {
        this.homeBTN = homeBTN;
    }

    public void setPlayAgainBTN(Button playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public int[] getWinType() {
        return winType;
    }
    //if playernames null --noone entered names, use getPlayerNames OR in GameActivity say if getIntent array null populate with data Player 1 pl 2
}
