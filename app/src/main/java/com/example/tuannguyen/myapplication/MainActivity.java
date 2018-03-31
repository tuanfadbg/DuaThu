package com.example.tuannguyen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView score;
    ImageButton btn_play_2;
    SeekBar seekBar1, seekBar2, seekBar3;
    int maxProgress = 300;
    int race = 100;
    int bird;
    int textscore;
    int defaultScore = 100;
    boolean countDownTimerDone = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        define();

        bird = getIntent().getIntExtra("BIRD", 0);
        textscore = getIntent().getIntExtra("SCORE", defaultScore);
        Log.e("bird", bird + "");

        score.setText(textscore + "");

        seekBar1.setMax(maxProgress);
        seekBar2.setMax(maxProgress);
        seekBar3.setMax(maxProgress);

        final int allTime = 60000;
        final int countDownInterval = 100;

        CountDownTimer countDownTimer = new CountDownTimer(allTime, countDownInterval) {
            int time = 0;
            int het = allTime / countDownInterval * 3 / 5;
            boolean check = false;
            boolean stop = false;
            boolean checkAndNext = true;
            @Override
            public void onTick(long l) {
                time++;
//                Log.e("time", time + "");
                if (!stop) {
                    if (time > het)
                        check = setRace(true);
                    else
                        check = setRace(false);
                }
                if (check) {

                    // android 4.44 and lower
                    stop = true;

                    if (checkAndNext)
                        checkScoreAndSelectBird(bird);
                    checkAndNext = false;
                    // end android 4.4.4 and lower
                    this.cancel();
                }
            }

            @Override
            public void onFinish() {

            }
        };

        countDownTimer.start();
        Log.e("break", "hi");
        // on click button play show popup select bird
        btn_play_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(textscore);
            }
        });

    }

    protected void checkScoreAndSelectBird(int bird) {

        int winner = getWinner();

        Log.e("winner", winner + "");
        if (winner == bird) {
            textscore += 10;
            Toast.makeText(getApplicationContext(), R.string.you_winned, Toast.LENGTH_LONG)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.your_lost, Toast.LENGTH_LONG)
                    .show();
            textscore -= 10;
        }

        score.setText(textscore + " ");
//
//        SystemClock.sleep(3000); // 3s
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showPopup(textscore);
                btn_play_2.setVisibility(View.VISIBLE);
            }
        },3000 );
//        Intent intent = new Intent(this, Popup.class);
//        intent.putExtra("SCORE", textscore);
//        startActivity(intent);
    }
    protected void showPopup(int textscore) {

        Intent intent = new Intent(MainActivity.this, Popup.class);
        intent.putExtra("SCORE", textscore);
        startActivity(intent);
    }
    protected boolean setRace(boolean isBreak) {
        Random rand = new Random();
        int one, two, three;
        int addAfterInterval = 1;

        one = seekBar1.getProgress();
        two = seekBar2.getProgress();
        three = seekBar3.getProgress();

        one += rand.nextInt(5);
        two += rand.nextInt(5);
        three += rand.nextInt(5);

        int max = one;
        if (max < two)
            max = two;
        if (max < three)
            max = three;

        if (!isBreak) {
            int min = one;

            if (min > two)
                min = two;
            if (min > three)
                min = three;


            if (min > race) {
                int temp = min - race;
                race += addAfterInterval;
                one -= temp;
                two -= temp;
                three -= temp;
            }
        }

        seekBar1.setProgress(one);
        seekBar2.setProgress(two);
        seekBar3.setProgress(three);
        if (max >= maxProgress) {
            return true;
        } else
            return false;
    }

    protected int getWinner() {
        int one = seekBar1.getProgress();
        int two = seekBar2.getProgress();
        int three = seekBar3.getProgress();
        Log.e("one", one + "");
        Log.e("two", two + "");
        Log.e("three", three + "");
        int max = one;
        int pos = 1;
        if (max < two) {
            max = two;
            pos = 2;
        }
        if (max < three) {
            max = three;
            pos = 3;
        }
//        Toast.makeText(getApplicationContext(), pos + " winned", Toast.LENGTH_LONG).show();
        return pos;
    }

    protected void define() {
        score = findViewById(R.id.score);
        btn_play_2 = findViewById(R.id.btn_play_2);
        seekBar1 = findViewById(R.id.SeekBar1);
        seekBar2 = findViewById(R.id.SeekBar2);
        seekBar3 = findViewById(R.id.SeekBar3);
        seekBar1.setEnabled(false);
        seekBar2.setEnabled(false);
        seekBar3.setEnabled(false);
    }
}
