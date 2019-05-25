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
import android.widget.SeekBar;
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

        optimalTrials.setText(String.format("Optimal Guesses : %s", optimalGuess.toString()));

        //landscape

        final SeekBar guessNum2 = (SeekBar) findViewById(R.id.guessNum2);
        guessNum2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                TextView seekbarValue = (TextView)findViewById(R.id.seekbarValue);
                seekbarValue.setText(String.format("%d / %d", guessNum2.getProgress(), guessNum2.getMax()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Button check = (Button)findViewById(R.id.check);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        trials++;
                        trialsOverSet();
                        Integer guessedNum = guessNum2.getProgress();
                        int result = compare(numToGuess,guessedNum);
                        setBgColor(Math.abs(numToGuess-guessedNum));
                        result(result,optimalGuess);
                        lastGuessSet(guessedNum);
                    }
                });

            }
        });


        //potrait

        Button check = (Button)findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                EditText guessNum = (EditText) findViewById(R.id.guessNum);
                if(!guessNum.getText().toString().matches("")){
                    int guessedNum = Integer.parseInt(guessNum.getText().toString());
                    if(guessedNum >= 1 && guessedNum <= 100){
                        trials++;
                        trialsOverSet();
                        int result = compare(numToGuess,guessedNum);
                        setBgColor(Math.abs(numToGuess-guessedNum));
                        result(result,optimalGuess);
                        lastGuessSet(guessedNum);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),getString(R.string.warn),Toast.LENGTH_LONG).show();
                    }
                    guessNum.setText("");
                }
                else{
                    Toast.makeText(getApplicationContext(),getString(R.string.warn2),Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    //compare guessed number and original number
    public int compare(int numToGuess,int guessedNum){
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

    //finding optimal trials using binary search
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

    //change bg color based on number guessed
    public void setBgColor(int diff){
        if(diff == 0){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff0));
        }
        else if(diff < 5){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff0));
        }
        else if(diff < 10){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff10));
        }
        else if(diff < 15){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff15));
        }
        else if(diff < 20){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff20));
        }
        else if(diff < 25){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff25));
        }
        else if(diff < 30){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff30));
        }
        else if(diff < 35){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff35));
        }
        else if(diff < 40){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff40));
        }
        else if(diff < 45){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff45));
        }
        else if(diff < 50){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff50));
        }
        else if(diff < 55){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff55));
        }
        else if(diff < 60){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff60));
        }
        else if(diff < 65){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff65));
        }
        else if(diff < 70){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff70));
        }
        else if(diff < 75){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff75));
        }
        else if(diff < 80){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff80));
        }
        else if(diff < 85){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff85));
        }
        else if(diff < 90){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff90));
        }
        else if(diff < 95){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff95));
        }
        else if(diff <= 100){
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.diff100));
        }
    }

    //Increments Win value
    private void Increment(){
        wins++;
    }

    //Increments Loss value
    private void decrement(){
        loss++;
    }

    //Intent call to mainactivity
    private void intentCall(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("wins",wins);
        i.putExtra("loss",loss);
        startActivity(i);
    }

    private void trialsOverSet(){
        TextView trialsOver = (TextView) findViewById(R.id.trialsOver);
        trialsOver.setText(String.format("%s%s", getString(R.string.trials_over), Integer.toString(trials)));
    }
    private void lastGuessSet(int guessedNum){
        TextView lastGuess = (TextView)findViewById(R.id.lastGuess);
        lastGuess.setText(String.format("Last Guess: %s", Integer.toString(guessedNum)));
    }

    private void result(int res,int optimalGuess){
        if(res == 0 && trials <= optimalGuess){
            Toast.makeText(getApplicationContext(),getString(R.string.winyay),Toast.LENGTH_LONG).show();
            Increment();
            TextView winText = (TextView)findViewById(R.id.wins);
            winText.setText(String.format("Wins: %s", Integer.toString(wins)));
            intentCall();
        }
        else if(res == 0){
            Toast.makeText(getApplicationContext(),getString(R.string.fail3),Toast.LENGTH_LONG).show();
            decrement();
            intentCall();
        }
        else if (res == -1) {
            Toast.makeText(getApplicationContext(),R.string.failLess,Toast.LENGTH_LONG).show();
        }
        else if (res == 1) {
            Toast.makeText(getApplicationContext(),R.string.failhigh,Toast.LENGTH_LONG).show();
        }
    }
}
