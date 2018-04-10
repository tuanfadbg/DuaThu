package com.example.tuannguyen.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

public class DialogSelectBird {
    ImageButton bird1, bird2, bird3;

    public int getBird() {
        return bird;
    }

    int bird = 0;
    public Dialog showDialogSelectBird(Context context, DisplayMetrics dm) {

        Dialog selectBirdDialog = new Dialog(context);
        selectBirdDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectBirdDialog.setContentView(R.layout.popup);
        selectBirdDialog.setCanceledOnTouchOutside(false);


        selectBirdDialog.show();

        // lay kich thuoc man hinh

        Window window = selectBirdDialog.getWindow();
        window.setLayout((dm.widthPixels)*8/10, (dm.heightPixels)*6/10);

//        define(selectBirdDialog);
//
//        getSelectBird(bird1);
//        getSelectBird(bird2);
//        getSelectBird(bird3);
        return selectBirdDialog;
    }

    public void define(Dialog selectBirdDialog) {
        bird1 = selectBirdDialog.findViewById(R.id.bird_1);
        bird2 = selectBirdDialog.findViewById(R.id.bird_2);
        bird3 = selectBirdDialog.findViewById(R.id.bird_3);
    }
}
