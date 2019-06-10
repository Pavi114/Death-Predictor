package com.example.pavithra.connect4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MultiPlayer extends AppCompatActivity {

    GamePlay gamePlay;
    TextView player1;
    TextView player2;
    LinearLayout layout;
    Button undo;
    int option;
    int player = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_multi);
        gamePlay = (GamePlay)findViewById(R.id.board);
        player1 = (TextView)findViewById(R.id.player1);
        player2 = (TextView)findViewById(R.id.player2);

        setPlayerColor();

        undo = (Button)findViewById(R.id.undo);

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = gamePlay.undo();
                if(status == -1){
                    Toast.makeText(getApplicationContext(),"No More Moves to Undo",Toast.LENGTH_LONG).show();
                }
            }
        });

        gamePlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        gamePlay.drop(x,y);
                        player = gamePlay.checkForWin();
                        setPlayerColor();
                        if(player > 0){
                            win();
                        }
                        else if(player == -1){
                            draw();
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void setPlayerColor(){
      int player = gamePlay.getPlayer();
      if(player == 1){
          player1.setBackground(getResources().getDrawable(R.drawable.red_gradient));
          player2.setBackground(getResources().getDrawable(R.drawable.player_bg));
      }
      else if(player == 2){
          player2.setBackground(getResources().getDrawable(R.drawable.green_gradient));
          player1.setBackground(getResources().getDrawable(R.drawable.player_bg));
      }
    }

    public void win(){
        Toast.makeText(getApplicationContext(),"Player " + gamePlay.getPlayerWon() + " won",Toast.LENGTH_LONG).show();
        intentCall();
    }

    public void draw(){
        Toast.makeText(getApplicationContext(),"Game Draw",Toast.LENGTH_LONG);
        intentCall();
    }

    public void intentCall(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
