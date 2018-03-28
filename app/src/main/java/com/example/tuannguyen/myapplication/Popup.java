package com.example.tuannguyen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Tuan Nguyen on 17/03/2018.
 */

public class Popup extends AppCompatActivity{
    ImageButton bird1, bird2, bird3;
    int bird = 0;
    int score;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        score = getIntent().getIntExtra("SCORE", 100);
        define();

        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        int weight = displayMetrics.widthPixels;

        getWindow().setLayout((int) (weight*0.8), (int) (height*0.6));
        getSelectBird(bird1);
        getSelectBird(bird2);
        getSelectBird(bird3);


    }
    public void getSelectBird(final ImageButton imageButton) {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (imageButton.getId()) {
                    case R.id.bird_1:
                        bird = 1;
                        break;
                    case R.id.bird_2:
                        bird = 2;
                        break;
                    case R.id.bird_3:
                        bird = 3;
                        break;
                }

                Intent intent = new Intent(Popup.this, MainActivity.class);
                intent.putExtra("BIRD", bird);
                intent.putExtra("SCORE", score);
                startActivity(intent);
            }
        });
    }
    protected void define() {
        bird1 = findViewById(R.id.bird_1);
        bird2 = findViewById(R.id.bird_2);
        bird3 = findViewById(R.id.bird_3);
    }

}

