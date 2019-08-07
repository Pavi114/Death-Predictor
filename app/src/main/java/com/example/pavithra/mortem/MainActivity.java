

package com.example.pavithra.mortem;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static int wins = 0;
    static int loss = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int Wins,Loss;
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null){
            Wins = bd.getInt("wins");
            Loss = bd.getInt("loss");
            TextView winText = (TextView)findViewById(R.id.wins);
            TextView lossText = (TextView)findViewById(R.id.loss);
            if(Wins > wins){
                getWindow().getDecorView().setBackgroundColor(Color.parseColor("#00FF00"));
            }
            else if(Loss > loss){
                getWindow().getDecorView().setBackgroundColor(Color.parseColor("#FF0000"));
            }
            wins = Wins;
            loss = Loss;
            winText.setText(String.format("Wins: %s", Integer.toString(wins)));
            lossText.setText(String.format("Loss: %s", Integer.toString(loss)));
        }


        Button guess = (Button)findViewById(R.id.guess);
        Button reset = (Button)findViewById(R.id.reset);

        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text1 = (EditText)findViewById(R.id.orgNum);
                if(!(text1.getText().toString().matches("")) ){
                    int numGuess = Integer.parseInt(text1.getText().toString());
                    if(numGuess < 1 || numGuess > 100){
                        Toast.makeText(getApplicationContext(),"Enter values Between 1-100",Toast.LENGTH_LONG).show();
                    }else{
                        Intent i = new Intent(getApplicationContext(), checkActivity.class);
                        i.putExtra("numGuess",numGuess);
                        i.putExtra("wins",wins);
                        i.putExtra("loss",loss);
                        startActivity(i);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please Enter values",Toast.LENGTH_SHORT).show();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wins = 0;
                loss = 0;
                TextView winText = (TextView)findViewById(R.id.wins);
                TextView lossText = (TextView)findViewById(R.id.loss);
                EditText text1 = (EditText)findViewById(R.id.orgNum);
                text1.setText("");
                winText.setText(String.format("Wins: %s", Integer.toString(wins)));
                lossText.setText(String.format("Loss: %s", Integer.toString(loss)));
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.bgColor));
            }
        });

    }
}

