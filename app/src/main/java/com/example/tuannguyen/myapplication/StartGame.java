package com.example.tuannguyen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    ImageButton btnStart;

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
                if (name.length() == 0)
                    Toast.makeText(getApplicationContext(), R.string.please_enter_your_name, Toast.LENGTH_SHORT).show();
                else {
                    selectBird();
                }
            }
        });
    }

    protected void selectBird() {
        startActivity(new Intent(StartGame.this, Popup.class));
    }
    protected void define() {
        editName = findViewById(R.id.edit_text_name);
        btnStart = findViewById(R.id.btn_start);
    }
}
