package com.example.fx504.praktikum.admin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.model.RespAddNovel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNovel extends AppCompatActivity {

    APIService apiService;

    TextView tv_statusAdd;
    ImageView iv_novelCover;
    Spinner spin_genre;

    Button btn_addStory;
    Button btn_saveNovel;

    EditText et_novelTitle;
    EditText et_novelDesc;

    static final int CODE_IMAGE = 1000;
    static final int CODE_PDF = 2000;

    String Genre;
    String ImgCover;
    String PDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noveladd);

        apiService = APIClient.getService();

        spin_genre     = findViewById(R.id.spin_genre);
        iv_novelCover  = findViewById(R.id.iv_novelCover);
        tv_statusAdd   = findViewById(R.id.tv_statusAdd);

        btn_addStory   = findViewById(R.id.btn_addStory);
        btn_saveNovel  = findViewById(R.id.btn_saveNovel);
        et_novelTitle  = findViewById(R.id.et_novelTitle);
        et_novelDesc  = findViewById(R.id.et_novelDesc);



        setSpin_genre();
        changeImage();
        setBtn_addStory();
        setBtn_saveNovel();
    }

    // Set Spinner genre item and max height
    public void setSpin_genre(){
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spin_genre);

            // Set popupWindow height to 500px
            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.genre_list,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_genre.setAdapter(adapter);

        spin_genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String genre = parent.getItemAtPosition(position).toString();
                getGenre(genre);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Add Image from gallery
    public void changeImage () {
        iv_novelCover.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AddNovel.this,
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

    @SuppressLint("IntentReset")
    private void startGallery() {
        Intent takeImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeImage.setType("image/*");
        if (takeImage.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takeImage, CODE_IMAGE);
        }
    }

    public void setBtn_addStory() {
        btn_addStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent,"Select PDF"),CODE_PDF);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == CODE_IMAGE) {
                Uri returnUri = data.getData();
                String pathImg = returnUri.getPath();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iv_novelCover.setImageBitmap(bitmapImage);
                getBitmapImg(pathImg);
                tv_statusAdd.setText(pathImg);
            }
            if (requestCode == CODE_PDF){
                Uri pdfFolder = data.getData();
                String pathPDF = pdfFolder.getPath();
                getUriPDF(pathPDF);
//
                if (!data.equals("")){
                    tv_statusAdd.setText(""+pathPDF);
                    tv_statusAdd.setTextColor(this.getResources().getColor(R.color.colorUpdate));

                }else {
                    tv_statusAdd.setText("Gagal Menyimpan");
                    tv_statusAdd.setTextColor(this.getResources().getColor(R.color.colorAllert));
                }

            }
        }
    }

    public void setBtn_saveNovel(){
        btn_saveNovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String novel_title = et_novelTitle.getText().toString();
                final String novel_synopsis = et_novelDesc.getText().toString();
                if (!novel_title.equals("") && !novel_synopsis.equals("") && PDF!=null
                        && !Genre.equals("") && ImgCover !=null){

//                    apiService.AddNovel(novel_title,Genre,novel_synopsis,PDF.getPath())
//                            .enqueue(new Callback<ResponseAdd>() {
//                                @Override
//                                public void onResponse(Call<ResponseAdd> call, Response<ResponseAdd> response) {
//                                    if (response.isSuccessful()){
//                                        Toast.makeText(AddNovel.this, "Data Suskses tersimpan", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                                @Override
//                                public void onFailure(Call<ResponseAdd> call, Throwable t) {
//                                    Toast.makeText(AddNovel.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
//                                    Log.wtf("errorAdd",t);
//                                }
//                            });

                    File pathPDF = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                    pathPDF.mkdir();

                    File pathImg = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    pathImg.mkdir();

                    File file = new File(pathPDF, String.valueOf(PDF));
                    File img = new File(pathImg, String.valueOf(ImgCover));

                    RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    RequestBody reqImg = RequestBody.create(MediaType.parse("multipart/form-data"),img);

                    RequestBody title = RequestBody.create(MultipartBody.FORM,novel_title);
                    RequestBody genre = RequestBody.create(MultipartBody.FORM,Genre);
                    RequestBody synopsis = RequestBody.create(MultipartBody.FORM,novel_synopsis);
                    MultipartBody.Part pdfFile = MultipartBody.Part.createFormData("StoryFile",file.getName(),reqFile);
                    MultipartBody.Part imgFile = MultipartBody.Part.createFormData("ImgCover",img.getName(),reqImg);

                    apiService.NewNovels(title,genre,synopsis,pdfFile,imgFile)
                            .enqueue(new Callback<RespAddNovel>() {
                                @Override
                                public void onResponse(retrofit2.Call<RespAddNovel> call, Response<RespAddNovel> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(AddNovel.this, "Data Suskses tersimpan", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(AddNovel.this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(retrofit2.Call<RespAddNovel> call, Throwable t) {
                                    Toast.makeText(AddNovel.this, "Sistem Error "+t, Toast.LENGTH_SHORT).show();
                                    Log.wtf("errorAdd",t);
                                }
                            });
                }else {
                    Toast.makeText(AddNovel.this, "Masukan data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getGenre(String genre){
        Genre = genre;
    }
    private void getUriPDF(String uri){
        PDF = "document/mylife.pdf";
    }
    private void getBitmapImg(String img){
        ImgCover = "image/mylife.jpeg";
    }

}
