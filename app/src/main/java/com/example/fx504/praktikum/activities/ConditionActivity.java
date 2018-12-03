package com.example.fx504.praktikum.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.fx504.praktikum.admin.HomeAdmin;
import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.model.SharePref;

public class ConditionActivity extends Activity {

    int SET_TIMER = 1500;
    ImageView iv_logo;
    SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        sharePref = new SharePref(this);

        spashscreen();

    }

    public void setView_logo() {
        iv_logo = findViewById(R.id.iv_logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.myanim);
        iv_logo.startAnimation(animation);
    }

    public void spashscreen(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String token = sharePref.getDataString(SharePref.KEY_TOKEN);
                Intent intent;

                
                // Checking already login or not
                if (token.equals("")){
                    intent = new Intent(ConditionActivity.this, FullScreenActivity.class);
                    startActivity(intent);

                }else {
                    //Checking member or admin
                    int status = sharePref.getDataInt(SharePref.KEY_STATUS);
                    if (status==1){
                        intent = new Intent(ConditionActivity.this, HomeAdmin.class);
                    }else {
                        intent = new Intent(ConditionActivity.this, MainActivity.class);
                    }
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
