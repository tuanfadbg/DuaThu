package com.example.tuannguyen.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.view.WindowManager.*;

/*.p*
 * Created by Tuan Nguyen on 16/03/2018.
 */

public class StartGame extends AppCompatActivity {
    EditText editName;
    ImageButton btnStart, bird1, bird2, bird3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.start_game);

        define();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                if (name.length() == 0) // kiểm tra xem đã điền tên chưa
                    Toast.makeText(getApplicationContext(), R.string.please_enter_your_name, Toast.LENGTH_SHORT).show();
                else {
                    // nếu đã điền tên rồi thì lấy kích thước màn hình
                    DisplayMetrics dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);


                    DialogSelectBird dialogSelectBird = new DialogSelectBird();

                    Dialog dialogSelected = dialogSelectBird.showDialogSelectBird(StartGame.this, dm);

                    defineDialog(dialogSelected);
                    // bat su kien khi chon chim
                    onSelectBirdAndStartIntent(bird1);
                    onSelectBirdAndStartIntent(bird2);
                    onSelectBirdAndStartIntent(bird3);

                }
            }
        });

    }

    protected void onSelectBirdAndStartIntent(final ImageButton imageButton) {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bird = 0;
                switch (imageButton.getId()) {
                    case R.id.bird_1:
                        bird = 1;
                        break;
                    case R.id.bird_2:
                        bird = 2;
                        break;
                    case R.id.bird_3:
                        bird= 3;
                        break;
                }
//                Log.e("bird", bird + "");

                Intent goToMainActivity = new Intent(StartGame.this, MainActivity.class);
                goToMainActivity.putExtra("BIRD", bird);
                startActivity(goToMainActivity);
            }
        });
    }


    protected void define() {
        editName = findViewById(R.id.edit_text_name);
        btnStart = findViewById(R.id.btn_start);
    }
    protected void defineDialog(Dialog dialog) {
        bird1 = dialog.findViewById(R.id.bird_1);
        bird2 = dialog.findViewById(R.id.bird_2);
        bird3 = dialog.findViewById(R.id.bird_3);
    }
}
