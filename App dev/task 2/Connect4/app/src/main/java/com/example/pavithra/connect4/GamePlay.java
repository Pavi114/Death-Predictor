package com.example.pavithra.connect4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import java.util.Stack;

public class GamePlay extends View {

    int numCols = 7;
    int numRows = 6;

    int sizeCell;
    int playerWon = 0;
    int rowTouched = -1;
    int colTouched = -1;
    Stack<Integer> stack = new Stack<Integer>();

    public int getPlayerWon() {
        return playerWon;
    }

    public int getPlayer() {
        return player;
    }

    int[][] board = new int[numRows][numCols];
    int player = 1;
    Rect rect = new Rect();
    Paint color = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GamePlay(Context context,AttributeSet attrs) {
        super(context,attrs);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        sizeCell = (widthPixels - 100)/numCols;
    }

     @Override
     protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

         rect.set(0,0,numCols*sizeCell,numRows*sizeCell);
         color.setAlpha(0);
         color.setColor(Color.parseColor("#200122"));
         color.setStyle(Paint.Style.FILL);
         canvas.drawRect(rect, color);
         color.setColor(Color.parseColor("#403a3e"));
         color.setStyle(Paint.Style.STROKE);
         canvas.drawRect(rect, color);

         if(checkForWin() == 0) {
             for (int i = 0; i < numRows; i++) {
                 for (int j = 0; j < numCols; j++) {
                     color.setColor(Color.rgb(255, 224, 189));
                     color.setAlpha(255);
                     color.setStyle(Paint.Style.FILL);
                     canvas.drawCircle((float) (j + 0.5) * sizeCell, (float) (i + 0.5) * sizeCell, 50, color);


                     if (board[i][j] > 0) {
                         color.setColor(Color.rgb((board[i][j] == 1 ? 65 : 0), (board[i][j] == 2 ? 65 : 0), 0));
                         color.setAlpha(200);
                         canvas.drawCircle((float) (j + 0.5) * sizeCell, (float) (i + 0.5) * sizeCell, 48, color);
                     }
                 }
             }
         }
         invalidate();
     }

     public void drop(int x,int y){
         colTouched = x/sizeCell;
         if(colTouched < numCols) {
             for (int j = numRows - 1; j >= 0; j--) {
                 if (board[j][colTouched] == 0) {
                     rowTouched = j;
                     board[rowTouched][colTouched] = player;
                     pushStack(rowTouched,colTouched);
                     player = (player == 1 ? 2 : 1);
                     break;
                 }
             }
         }
     }

     public void pushStack(int row,int col){
        stack.push(row);
        stack.push(col);
     }

     public int undo(){
        if(!stack.empty()){
            Integer col = stack.pop();
            Integer row = stack.pop();
            board[row][col] = 0;
            invalidate();
            player = (player == 1 ? 2 : 1);

            return 1;
        }
         return -1;
     }

    public int piecePos(int y,int x){
        return ((y<0 || x>=numCols || x<0 || y>= numRows)? 0 : board[y][x]);
    }

    public int checkForWin(){

        //horizontal check
        for(int y = 0;y<numRows;y++){
            for(int x = 0;x<numCols;x++){
                if(piecePos(y,x) != 0 && piecePos(y,x+1) == piecePos(y,x)
                        && piecePos(y,x+2) == piecePos(y,x) && piecePos(y,x+3) == piecePos(y,x)){
                    playerWon = piecePos(y,x);
                    return piecePos(y,x);
                }
            }
        }

        //vertical check
        for(int y = 0;y<numRows;y++){
            for(int x = 0;x<numCols;x++){
                if(piecePos(y,x) != 0 && piecePos(y+1,x) == piecePos(y,x)
                        && piecePos(y+2,x) == piecePos(y,x) && piecePos(y+3,x) == piecePos(y,x)){
                    playerWon = piecePos(y,x);
                    return piecePos(y,x);
                }
            }
        }

        //top-left to bottom right diagonals
        for(int y = 0; y <= numRows - 4; y++){
            int row, col;
            for( row = y, col = 0; row < numRows && col < numCols; row++, col++ ){
                if(piecePos(row,col) != 0 && piecePos(row+1,col+1) == piecePos(row,col)
                        && piecePos(row+2,col+2) == piecePos(row,col) && piecePos(row+3,col+3)== piecePos(row,col)){
                    playerWon = piecePos(row,col);
                    return piecePos(row,col);
                }
            }
        }

        for(int x = 1; x <= numCols - 4; x++){
            int row, col;
            for( row = 0, col = x; row < numRows && col < numCols; row++, col++ ){
                if(piecePos(row,col) != 0 && piecePos(row+1,col+1) == piecePos(row,col)
                        && piecePos(row+2,col+2) == piecePos(row,col) && piecePos(row+3,col+3)== piecePos(row,col)){
                    playerWon = piecePos(row,col);
                    return piecePos(row,col);
                }
            }
        }

        //top right to bottom left diagonals
        for(int y = 0;y <= numRows - 4;y++){
            int row,col;
            for(row = y,col = numCols - 1;row < numRows && col >= 0;col--,row++){
                if(piecePos(row,col) != 0 && piecePos(row+1,col-1) == piecePos(row,col)
                        && piecePos(row+2,col-2) == piecePos(row,col) && piecePos(row+3,col-3)== piecePos(row,col)){
                    playerWon = piecePos(row,col);
                    return piecePos(row,col);
                }
            }
        }

        for(int x = 3;x <= numCols - 1;x++){
            int row,col;
            for(row = 0,col = x; row <= numRows && col <= numCols;row++,col--){
                if(piecePos(row,col) != 0 && piecePos(row+1,col-1) == piecePos(row,col)
                        && piecePos(row+2,col-2) == piecePos(row,col) && piecePos(row+3,col-3)== piecePos(row,col)){
                    playerWon = piecePos(row,col);
                    return piecePos(row,col);
                }
            }
        }

        //empty board
        for(int y = 0;y<numRows;y++) {
            for (int x = 0; x < numCols; x++) {
                if (piecePos(y, x) == 0) {
                    return 0;
                }
            }
        }

        return -1;
    }
}
