package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class TicTacToeBoard extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;

    private final Paint paint = new Paint();
    private final GameLogic game;

    private int cellSize = getWidth() / 3;
    private float indent = (float) (cellSize * 0.2);
    private boolean winningLine = false;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new GameLogic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard,
                0, 0);
        try {
            boardColor = a.getInt(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInt(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInt(R.styleable.TicTacToeBoard_OColor, 0);
            winningLineColor = a.getInt(R.styleable.TicTacToeBoard_winningLineColor, 0);
        } finally {
            a.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int dimension = Math.min(getMeasuredHeight(), getMeasuredWidth());

        cellSize = dimension / 3;
        indent = (float) (cellSize * 0.2);
        setMeasuredDimension(dimension, dimension);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawBoardGame(canvas);
        drawMarkers(canvas);

        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }

    }

    private void drawMarkers(Canvas canvas) {
        //must know which players turn it is - draw that shape
        //to draw shape must know col and r
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (game.getGameBoard()[r][c] != 0) {
                    //has value 1 - x, or 2 - o
                    if (game.getGameBoard()[r][c] == 1) {
                        drawX(canvas, r, c);
                    } else
                        drawO(canvas, r, c);
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //check for int
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            int row = (int) Math.ceil(y / cellSize);
            int col = (int) Math.ceil(x / cellSize);

            //but when game is reseted update this winningLine to false - we wont be able to place any X or O
            if (!winningLine) {
                //this enables user to place X or O
                if (game.updateGameBoard(row, col)) {
                    //draw and change player
                    invalidate();
                    //by here user has played ---check if he placed a winning move
                    if (game.winnerCheck()) {
                        winningLine = true;
                    }
                    //if it is - board must be updated to display who won and code under shouldnt be run
                    invalidate();
                    if (game.getPlayer() % 2 == 0) {
                        //pl 2, -1
                        game.setPlayer(game.getPlayer() - 1);
                    } else {
                        game.setPlayer(game.getPlayer() + 1);
                    }
                }
            }
            //needed?
            invalidate();

            return true;
        }

        return false;
    }


    private void drawBoardGame(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for (int c = 1; c < 3; c++) {
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getHeight(), paint);
        }

        for (int r = 1; r < 3; r++) {
            canvas.drawLine(0, cellSize * r, canvas.getWidth(), cellSize * r, paint);
        }

    }

    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(XColor);

        canvas.drawLine(col * cellSize + indent, row * cellSize + indent,
                (col + 1) * cellSize - indent, (row + 1) * cellSize - indent,
                paint);

        canvas.drawLine((col + 1) * cellSize - indent, row * cellSize + indent,
                col * cellSize + indent, (row + 1) * cellSize - indent,
                paint);

    }

    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(OColor);

        canvas.drawOval(col * cellSize + indent, row * cellSize + indent,
                (col + 1) * cellSize - indent, (row + 1) * cellSize - indent, paint);

    }

    private void drawWinningLine(Canvas canvas){
        int row=game.getWinType()[0];
        int col=game.getWinType()[1];

        switch (game.getWinType()[2]){
            case 1:
                drawHorizontalLine(canvas, row, col);
                break;
            case 2:
                drawVerticalLine(canvas, row, col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
    }
    private void drawHorizontalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col, row * cellSize + (float) cellSize / 2,
                3 * cellSize, row * cellSize + (float) cellSize / 2,
                paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col * cellSize + (float) cellSize / 2, row,
                col * cellSize + (float) cellSize / 2, 3 * cellSize,
                paint);
    }

    private void drawDiagonalLinePos(Canvas canvas) {
        canvas.drawLine(0, 3 * cellSize, 3 * cellSize, 0,
                paint);
    }

    private void drawDiagonalLineNeg(Canvas canvas) {
        canvas.drawLine(0, 0, 3 * cellSize, 3 * cellSize,
                paint);
    }

    public void resetBoard() {
        game.resetGameBoard();
        winningLine = false;
    }

    public void setUpGame(Button homeBtn, Button playAgainBtn, TextView playerTurn, String[] names) {
        game.setHomeBTN(homeBtn);
        game.setPlayAgainBTN(playAgainBtn);
        game.setPlayerTurn(playerTurn);
        game.setPlayerNames(names);
    }
}
