package com.example.fx504.praktikum.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fx504.praktikum.admin.HomeAdmin;
import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.MainActivity;
import com.example.fx504.praktikum.model.ResponseLogin;
import com.example.fx504.praktikum.model.SharePref;
import com.victor.loading.rotate.RotateLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    APIService apiService;
    SharePref sharePref;

    LinearLayout layout_login;

    EditText et_username;
    EditText et_password;

    Button btn_login;
    TextView tv_signUp;

    RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        sharePref = new SharePref(this);
        apiService = APIClient.getService();

        // Find ID

        layout_login = findViewById(R.id.layout_login);

        et_username  = findViewById(R.id.et_username);
        et_password  = findViewById(R.id.et_password);

        btn_login    = findViewById(R.id.btn_login);
        tv_signUp    = findViewById(R.id.tv_signUp);


        rotateLoading = findViewById(R.id.rotateloading);
//----------------------------USE FUNCTION----------------------------//
        //Login
        setBtn_login();

        //Sign Up
        setTv_signUp();

    }

//----------------------------LOGIN FUNCTION----------------------------//
    public void setBtn_login() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setLoading(true);
               callAPI();
               setLoading(false);
            }
        });
    }

    //Get data from API Service
    private void callAPI(){
        //get data input
        String user = et_username.getText().toString();
        String pass = et_password.getText().toString();
        apiService.LoginUser(user,pass).enqueue(new Callback<ResponseLogin>() {

            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()){

                    //save data user and show in Fragment Profile
                    assert response.body() != null;
                    sharePref.setDataString(SharePref.KEY_TOKEN,""+response.body().getToken());
                    sharePref.setDataString(SharePref.KEY_NAME,""+response.body().getUserName());
                    sharePref.setDataString(SharePref.KEY_PHONE,""+response.body().getUserTlfn());
                    sharePref.setDataString(SharePref.KEY_EMAIL,""+response.body().getUserEmail());
                    sharePref.setDataInt(SharePref.KEY_STATUS,response.body().getStatus());
                    sharePref.setDataInt(SharePref.KEY_ID,response.body().getUserId());

                    //status admin or member
                    int status = sharePref.getDataInt(SharePref.KEY_STATUS);
                    Toast.makeText(LoginActivity.this,
                            sharePref.getDataString(SharePref.KEY_NAME), Toast.LENGTH_SHORT).show();
                    setIntentStatus(status);
                }else {
                    Toast.makeText(LoginActivity.this, "Input Salah", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Intent by type user
    public void setIntentStatus(int status){
        Intent intent;
        if (status==1){
            intent = new Intent(this,HomeAdmin.class);
            startActivity(intent);

        }else {
            intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    }

//----------------------------REGISTRATION FUNCTION----------------------------//
    public void setTv_signUp(){
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to Activity Register
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

//----------------------------FUNCTION Button On Back Pressed----------------------------//
    @Override
    public void onBackPressed() {
        //Popup Dialog
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginActivity.super.onBackPressed();
                        finishAffinity();
                        System.exit(0);
                    }
                }).create().show();
    }


    public void setLoading(Boolean status){
        if (status){
            rotateLoading.start();
            layout_login.setVisibility(View.INVISIBLE);
        }else {
            rotateLoading.stop();
            layout_login.setVisibility(View.VISIBLE);
        }
    }

}
