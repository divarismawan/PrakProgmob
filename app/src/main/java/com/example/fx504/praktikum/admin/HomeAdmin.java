package com.example.fx504.praktikum.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.activities.LoginActivity;
import com.example.fx504.praktikum.model.SharePref;

public class HomeAdmin extends AppCompatActivity {

    CardView cv_addNovel;
    CardView cv_editNovel;
    CardView cv_deleteNovel;
    CardView cv_profileAdmin;

    Intent intent;

    SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);


        setCv_addNovel();
        setCv_profileAdmin();


    }

    public void setCv_addNovel() {
        cv_addNovel = findViewById(R.id.cv_addNovel);
        cv_addNovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeAdmin.this, AddNovel.class);
                startActivity(intent);
            }
        });
    }

    public void setCv_profileAdmin(){
        cv_profileAdmin = findViewById(R.id.cv_adminProfile);
        cv_profileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeAdmin.this)
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to exit?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                sharePref.clearData();
                                Intent intent = new Intent(HomeAdmin.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }).create().show();

            }
        });
    }
}
