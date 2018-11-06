package com.example.fx504.praktikum.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fx504.praktikum.activities.LoginActivity;
import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.model.SharePref;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment {

    CircleImageView img_profile;
    TextView tv_nama;
    TextView tv_email;
    TextView tv_phone;

    Button btn_logout;
    Button btn_imgEdit;


    SharePref sharePref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View frameView = inflater.inflate(R.layout.fragment_profile,container,false);

        sharePref = new SharePref(getActivity());

        img_profile = frameView.findViewById(R.id.img_profile);
        btn_imgEdit = frameView.findViewById(R.id.btn_editImg);

        tv_nama    = frameView.findViewById(R.id.tv_nama);
        tv_email   = frameView.findViewById(R.id.tv_email);
        tv_phone   = frameView.findViewById(R.id.tv_phone);
        btn_logout = frameView.findViewById(R.id.btn_logout);

        String nama     = sharePref.getDataString(SharePref.KEY_NAME);
        String email    = sharePref.getDataString(SharePref.KEY_EMAIL);
        String phone    = sharePref.getDataString(SharePref.KEY_PHONE);

        tv_nama.setText(""+nama);
        tv_email.setText(""+email);
        tv_phone.setText(""+phone);



        setBtn_logout();
        changeImage();
        return frameView;

    }

    public void setBtn_logout(){
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePref.setDataInt(SharePref.KEY_VALUE,0);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                img_profile.setImageBitmap(bitmapImage);
            }
        }
    }

    public void changeImage () {
        btn_imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                } else {
                    startGallery();
                }
            }
        });
    }

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

}
