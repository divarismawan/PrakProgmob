package com.example.fx504.praktikum.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.model.SharePref;

public class Condition extends Activity {

    int SET_TIMER = 1500;
    ImageView iv_logo;
    SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.condition);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        sharePref = new SharePref(this);



        spashscreen();

    }

    public void setIv_logo() {
        iv_logo = findViewById(R.id.iv_logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.myanim);
        iv_logo.startAnimation(animation);
    }

    public void spashscreen(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int myVal = sharePref.getDataInt(SharePref.KEY_VALUE, 0);
                Log.d("InputVal","Value Condition :"+myVal);
                Intent intent;
                if (myVal==1){

                    intent = new Intent(Condition.this, MainActivity.class);
                    startActivity(intent);

                }else {
                    intent = new Intent(Condition.this, FullScreenActivity.class);
                    startActivity(intent);
                }
                ///jeda selesai flashscreen
                this.finish();
            }

            private void finish() {
            }

        },SET_TIMER
        );
    }


}
