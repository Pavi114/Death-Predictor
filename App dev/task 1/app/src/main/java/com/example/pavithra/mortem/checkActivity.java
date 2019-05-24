package com.example.pavithra.mortem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class checkActivity extends AppCompatActivity {

    int trials = 0;
    int wins;
    int loss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        final Integer numToGuess = bd.getInt("numGuess");
        wins = bd.getInt("wins");
        loss = bd.getInt("loss");



        TextView winText = (TextView)findViewById(R.id.wins);
        winText.setText(String.format("Wins: %s", Integer.toString(wins)));

        TextView lossText = (TextView)findViewById(R.id.loss);
        lossText.setText(String.format("Loss: %s", Integer.toString(loss)));

        final TextView optimalTrials = (TextView)findViewById(R.id.optimalTrials);
        final Integer optimalGuess = binary(numToGuess);
        optimalTrials.append(optimalGuess.toString());

        Button check = (Button)findViewById(R.id.check);

        check.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                EditText guessNum = (EditText) findViewById(R.id.guessNum);
                TextView trialsOver = (TextView) findViewById(R.id.trialsOver);

                if(!guessNum.getText().toString().matches("")){
                    Integer guessedNum = Integer.parseInt(guessNum.getText().toString());
                    if(guessedNum < 1 || guessedNum > 100){
                        Toast.makeText(getApplicationContext(), "Please enter number from 1-100", Toast.LENGTH_SHORT).show();
                    }
                   else{
                        trials++;
                        trialsOver.setText("Trials Over: " + Integer.toString(trials));
                        int result = compare(numToGuess,guessedNum);
                        setBgColor(result,Math.abs(numToGuess-guessedNum));
                        TextView lastGuess = (TextView)findViewById(R.id.lastGuess);
                         if(trials < optimalGuess) {
                             if (result == 0) {
                                 LinearLayout ll = (LinearLayout)findViewById(R.id.checkAct2);
                                 LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                 lp.setMargins(0,10,0,0);
                                 lp.gravity=Gravity.CENTER_HORIZONTAL;
                                 Toast.makeText(getApplicationContext(),"Yayy! You can reap souls at time now",Toast.LENGTH_LONG).show();

                                 Button back = new Button(getApplicationContext());
                                 back.setText("Go Again");
                                 back.setLayoutParams(lp);
                                 back.setTextColor(getColor(R.color.colorPrimaryDark));
                                 back.setBackground(getResources().getDrawable(R.drawable.round_button));
                                 ll.addView(back);

                                 Increment();
                                 TextView winText = (TextView)findViewById(R.id.wins);
                                 winText.setText("Wins: " + Integer.toString(wins));

                                 back.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                         i.putExtra("wins",wins);
                                         i.putExtra("loss",loss);
                                         startActivity(i);
                                     }
                                 });



                             } else {
                                 if (result == -1) {
                                    Toast.makeText(getApplicationContext(),R.string.failLess,Toast.LENGTH_LONG).show();
                                 } else if (result == 1) {
                                     Toast.makeText(getApplicationContext(),R.string.failhigh,Toast.LENGTH_LONG).show();
                                 }
                                 lastGuess.setText("Last Guess: " + guessedNum.toString());

                             }
                         }
                         else if(trials == optimalGuess){
                             if(result == 0){
                                 LinearLayout ll = (LinearLayout)findViewById(R.id.checkAct);
                                 LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                 lp.setMargins(0,10,0,0);
                                 lp.gravity=Gravity.CENTER_HORIZONTAL;
                                 Toast.makeText(getApplicationContext(),"Yayy! You can reap souls at time now",Toast.LENGTH_LONG).show();
                                 
                                 Button back = new Button(getApplicationContext());
                                 back.setText("Go Again");
                                 back.setLayoutParams(lp);
                                 back.setTextColor(getColor(R.color.colorPrimaryDark));
                                 ll.addView(back);
                                 Increment();
                                 TextView winText = (TextView)findViewById(R.id.wins);
                                 winText.setText("Wins: " + Integer.toString(wins));

                                 back.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                         i.putExtra("wins",wins);
                                         i.putExtra("loss",loss);
                                         startActivity(i);
                                     }
                                 });
                             }
                             else{
                                 Toast.makeText(getApplicationContext(), R.string.lost, Toast.LENGTH_SHORT).show();
                                 decrement();
                                 Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                 i.putExtra("wins",wins);
                                 i.putExtra("loss",loss);
                                 startActivity(i);
                             }
                         }

                        else{
                             Toast.makeText(getApplicationContext(), R.string.lost, Toast.LENGTH_SHORT).show();
                             decrement();
                             Intent i = new Intent(getApplicationContext(),MainActivity.class);
                             i.putExtra("wins",wins);
                             i.putExtra("loss",loss);
                             startActivity(i);
                          }

                   }
                   guessNum.setText("");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Enter a value",Toast.LENGTH_SHORT).show();
                }


            }
        });



    }


    public int compare(Integer numToGuess,Integer guessedNum){
        int res;
        if(numToGuess == guessedNum){
            res = 0;
        }
        else if(numToGuess > guessedNum){
            res = -1;
        }
        else{
            res = 1;
        }
        return res;
    }

    public int binary(Integer num){
        int guess = 1;
        int[] numArray = new int[100];
        for(int i = 0; i < 100; i++){
            numArray[i] = i+1;
        }

        int beg = 0;
        int end = 99;
        int mid = (beg + end)/2;
        while(beg <= end){
            if(num == numArray[mid]){
                break;
            }
            else if(num > numArray[mid]){
                beg = mid + 1;
                guess++;
            }
            else{
                end = mid - 1;
                guess++;
            }

            mid = (beg + end)/2;
        }

        return guess;
    }

    public void setBgColor(int res,int diff){
        LinearLayout checkAct = (LinearLayout) findViewById(R.id.checkAct);
        if(diff == 0){
            checkAct.setBackgroundColor(getResources().getColor(R.color.Green));
        }
        else if(diff < 5){
            checkAct.setBackgroundColor(Color.parseColor("#24FF00"));
        }
        else if(diff < 10){
            checkAct.setBackgroundColor(Color.parseColor("#35FF00"));
        }
        else if(diff < 15){
            checkAct.setBackgroundColor(Color.parseColor("#47FF00"));
        }
        else if(diff < 20){
            checkAct.setBackgroundColor(Color.parseColor("#6AFF00"));
        }
        else if(diff < 25){
            checkAct.setBackgroundColor(Color.parseColor("#7CFF00"));
        }
        else if(diff < 30){
            checkAct.setBackgroundColor(Color.parseColor("#9FFF00"));
        }
        else if(diff < 35){
            checkAct.setBackgroundColor(Color.parseColor("#C2FF00"));
        }
        else if(diff < 40){
            checkAct.setBackgroundColor(Color.parseColor("#D4FF00"));
        }
        else if(diff < 45){
            checkAct.setBackgroundColor(Color.parseColor("#E5FF00"));
        }
        else if(diff < 50){
            checkAct.setBackgroundColor(Color.parseColor("#FFF600"));
        }
        else if(diff < 55){
            checkAct.setBackgroundColor(Color.parseColor("#FFD300"));
        }
        else if(diff < 60){
            checkAct.setBackgroundColor(Color.parseColor("#FFC100"));
        }
        else if(diff < 65){
            checkAct.setBackgroundColor(Color.parseColor("#FF9E00"));
        }
        else if(diff < 70){
            checkAct.setBackgroundColor(Color.parseColor("#FF8C00"));
        }
        else if(diff < 75){
            checkAct.setBackgroundColor(Color.parseColor("#FF7B00"));
        }
        else if(diff < 80){
            checkAct.setBackgroundColor(Color.parseColor("#FF5700"));
        }
        else if(diff < 85){
            checkAct.setBackgroundColor(Color.parseColor("#FF4600"));
        }
        else if(diff < 90){
            checkAct.setBackgroundColor(Color.parseColor("#FF2300"));
        }
        else if(diff < 95){
            checkAct.setBackgroundColor(Color.parseColor("#FF1100"));
        }
        else if(diff <= 100){
            checkAct.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }

    private void Increment(){
        wins++;
    }

    private void decrement(){
        loss++;
    }
}
