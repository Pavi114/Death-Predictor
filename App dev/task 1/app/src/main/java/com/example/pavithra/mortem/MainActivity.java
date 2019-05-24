

package com.example.pavithra.mortem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int wins = 0;
    int loss = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null){
            wins = bd.getInt("wins");
            loss = bd.getInt("loss");
            TextView winText = (TextView)findViewById(R.id.wins);
            TextView lossText = (TextView)findViewById(R.id.loss);
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
            }
        });

    }
}

