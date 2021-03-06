package com.example.tuannguyen.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity{
    TextView score;
    ImageButton btnSelectBird, bird1, bird2, bird3;
    SeekBar seekBar1, seekBar2, seekBar3;
    int maxProgress = 300;
    int minProgress = 0;
    int race = 100;
    int bird;
    int textscore;
    int boundRand = 5;
    int bonus = 45;
    int fines = 20;
    int DEFAULT_SCORE = 100;
    SharedPreferences dataGame;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //giữ màn hình luôn sáng
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        define();

        textscore = dataGame.getInt("score", DEFAULT_SCORE);
        bird = getIntent().getIntExtra("BIRD", 0);

        score.setText(textscore + "");

        seekBar1.setMax(maxProgress);
        seekBar2.setMax(maxProgress);
        seekBar3.setMax(maxProgress);

        CountDownTimer countDownTimer = createNewCountDownTimer();
        countDownTimer.start();

        // on click button play show popup select bird
        btnSelectBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopPopup();
            }
        });

    }

    private CountDownTimer createNewCountDownTimer() {
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
        return countDownTimer;

    }

    protected void checkScoreAndSelectBird(int bird) {

        int winner = getWinner();

        Log.e("winner", winner + "");
        if (winner == bird) {
            textscore += bonus;
            Toast.makeText(getApplicationContext(), R.string.you_winned, Toast.LENGTH_LONG)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.your_lost, Toast.LENGTH_LONG)
                    .show();
            textscore -= fines;
        }
        if (textscore < 0) {
            startNewGame();
        } else {
            dataGame = getSharedPreferences("data", MODE_PRIVATE);
            editor = dataGame.edit();
            editor.putInt("score", textscore);
            editor.apply();
            editor.commit();
            score.setText(textscore + " ");

            int delayMillis = 3000;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // get display metric
                    shopPopup();
                    // hien thi button select bird
                    btnSelectBird.setVisibility(View.VISIBLE);

                }
            }, delayMillis);
        }
    }

    private void startNewGame() {
        editor.clear();
        editor.apply();
        editor.commit();
        startActivity(new Intent(this, StartGame.class));

    }

    protected void shopPopup() {
        // nếu đã điền tên rồi thì lấy kích thước màn hình
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        DialogSelectBird dialogSelectBird = new DialogSelectBird();

        Dialog dialogSelected = dialogSelectBird.showDialogSelectBird(MainActivity.this, dm);

        StartGame startGame = new StartGame();

        bird1 = dialogSelected.findViewById(R.id.bird_1);
        bird2 = dialogSelected.findViewById(R.id.bird_2);
        bird3 = dialogSelected.findViewById(R.id.bird_3);

        // bat su kien khi chon chim
        bird = onSelectBirdAndDimissDialog(bird1, dialogSelected);
        bird = onSelectBirdAndDimissDialog(bird2, dialogSelected);
        bird = onSelectBirdAndDimissDialog(bird3, dialogSelected);

    }
    protected int onSelectBirdAndDimissDialog(final ImageButton imageButton, final Dialog dialog) {
        final int[] bird = {0};
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (imageButton.getId()) {
                    case R.id.bird_1:
                        bird[0] = 1;
                        break;
                    case R.id.bird_2:
                        bird[0] = 2;
                        break;
                    case R.id.bird_3:
                        bird[0] = 3;
                        break;
                }
//                Log.e("bird", bird + "");
                dialog.dismiss();
                startNewRace();
            }
        });
        return bird[0];
    }

    private void startNewRace() {

        btnSelectBird.setVisibility(View.INVISIBLE);

        seekBar1.setProgress(minProgress);
        seekBar2.setProgress(minProgress);
        seekBar3.setProgress(minProgress);
        //set score = default score
        editor.putInt("score", DEFAULT_SCORE);

        CountDownTimer countDownTimer = createNewCountDownTimer();
        countDownTimer.start();
    }

    protected boolean setRace(boolean isBreak) {
        Random rand = new Random();
        int one, two, three;
        int addAfterInterval = 1;

        one = seekBar1.getProgress();
        two = seekBar2.getProgress();
        three = seekBar3.getProgress();

        one += rand.nextInt(boundRand);
        two += rand.nextInt(boundRand);
        three += rand.nextInt(boundRand);

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
        return pos;
    }

    protected void define() {
        score = findViewById(R.id.score);
        btnSelectBird = findViewById(R.id.btn_select_bird);
        seekBar1 = findViewById(R.id.SeekBar1);
        seekBar2 = findViewById(R.id.SeekBar2);
        seekBar3 = findViewById(R.id.SeekBar3);
        seekBar1.setEnabled(false);
        seekBar2.setEnabled(false);
        seekBar3.setEnabled(false);
        dataGame = getSharedPreferences("data", MODE_PRIVATE);
        editor = dataGame.edit();
    }
}
