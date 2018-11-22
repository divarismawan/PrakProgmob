package com.example.fx504.praktikum.admin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.fx504.praktikum.model.Addnovel;
import com.example.fx504.praktikum.model.RespAddNovel;

import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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

    static final int CODE_IMAGE = 777;
    static final int CODE_PDF = 2000;

    String Genre;
    String imgPath;
    Uri PdfUri;
    Uri ImgUri;
    MultipartBody.Part novel_cover;


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
                Genre = genre;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static final int MY_PERMISSIONS_REQUEST_STORAGE= 1;
    private boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
            return false;
        } else {
            return true;
        }
    }

    //Add Image from gallery
    public void changeImage () {
        iv_novelCover.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkStoragePermission()) {
                        startGallery();
                    }
                }
                else {
                    startGallery();
                }
            }
        });
    }

    private void startGallery() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CODE_IMAGE);
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
        super.onActivityResult(requestCode,resultCode,data);
//        if () {

            if (requestCode == CODE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri ImgUrl = data.getData();

//                Bitmap bitmapImage = null;
                try {
                    Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), ImgUrl);
                    iv_novelCover.setImageBitmap(bitmapImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String filePath = getRealPathFromURI_API19(this,ImgUrl);
                File file = new File(filePath);

                RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                novel_cover = MultipartBody.Part.createFormData("novel_cover", file.getName(), reqFile);

            }

            if (requestCode == CODE_PDF){
                PdfUri = data.getData();
                if (!data.equals("")){
                    tv_statusAdd.setText(PdfUri.getPath());
                    tv_statusAdd.setTextColor(this.getResources().getColor(R.color.colorUpdate));
                }else {
                    tv_statusAdd.setText("Gagal Menyimpan");
                    tv_statusAdd.setTextColor(this.getResources().getColor(R.color.colorAllert));
                }

        }
    }

    public void setBtn_saveNovel(){
        btn_saveNovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String novel_title = et_novelTitle.getText().toString();
                final String novel_synopsis = et_novelDesc.getText().toString();

                if (!novel_title.equals("")){

                    //set data string to table tb_novel
                    RequestBody title = RequestBody.create(MultipartBody.FORM,novel_title);

                    //test
//                    tv_statusAdd.setText(ImgUri.getPath() + "S_Img : " + imgPath);
//                    Toast.makeText(AddNovel.this, ImgUri.toString(), Toast.LENGTH_SHORT).show();

//                    //import imgPath from onActivityResult
//                    File imgFile = new File(imgPath);
//                    //request type file
//                    RequestBody imgCover = RequestBody.create(MediaType.parse("image/*"), imgFile);
//                    novel_cover = MultipartBody.Part.createFormData("novel_cover",
//                            imgFile.getName(),imgCover);

                    //upload to api service

                    apiService.noveleee(title,novel_cover).enqueue(new Callback<Addnovel>() {
                        @Override
                        public void onResponse(Call<Addnovel> call, Response<Addnovel> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(AddNovel.this, "Data Suskses tersimpan", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddNovel.this, "Gagal Menyimpan"+response.errorBody(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Addnovel> call, Throwable t) {
                            Toast.makeText(AddNovel.this, "Error Menyimpan :"+t, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(AddNovel.this, "Masukan data", Toast.LENGTH_SHORT).show();
                }
//                if (!novel_title.equals("") && !novel_synopsis.equals("") && PDF!=null
//                        && !Genre.equals("") && ImgCover !=null){
//                    File pathPDF = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
//                    pathPDF.mkdir();
//
//                    File pathImg = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                    pathImg.mkdir();
//
//                    File file = new File(pathPDF, String.valueOf(PDF));
//                    File img = new File(pathImg, String.valueOf(ImgCover));
//
//                    RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
//                    RequestBody reqImg = RequestBody.create(MediaType.parse("multipart/form-data"),img);
//
//                    RequestBody title = RequestBody.create(MultipartBody.FORM,novel_title);
//                    RequestBody genre = RequestBody.create(MultipartBody.FORM,Genre);
//                    RequestBody synopsis = RequestBody.create(MultipartBody.FORM,novel_synopsis);
//                    MultipartBody.Part pdfFile = MultipartBody.Part.createFormData("StoryFile",file.getName(),reqFile);
//                    MultipartBody.Part imgFile = MultipartBody.Part.createFormData("ImgCover",img.getName(),reqImg);
//
//                    apiService.NewNovels(title,genre,synopsis,pdfFile,imgFile)
//                            .enqueue(new Callback<RespAddNovel>() {
//                                @Override
//                                public void onResponse(retrofit2.Call<RespAddNovel> call, Response<RespAddNovel> response) {
//                                    if (response.isSuccessful()){
//                                        Toast.makeText(AddNovel.this, "Data Suskses tersimpan", Toast.LENGTH_SHORT).show();
//                                    }else {
//                                        Toast.makeText(AddNovel.this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(retrofit2.Call<RespAddNovel> call, Throwable t) {
//                                    Toast.makeText(AddNovel.this, "Sistem Error "+t, Toast.LENGTH_SHORT).show();
//                                    Log.wtf("errorAdd",t);
//                                }
//                            });
//                }else {
//                    Toast.makeText(AddNovel.this, "Masukan data", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Izin diberikan.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        startGallery();
                    }

                } else {
                    // Izin ditolak.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    public static String getRealPathFromURI_API19(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

//                var cursor: Cursor? = null
//                try {
//                    cursor = context.contentResolver.query(uri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME),
// null, null, null)
//                    cursor!!.moveToNext()
//                    val fileName = cursor.getString(0)
//                    val path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
//                    if (!TextUtils.isEmpty(path)) {
//                        return path
//                    }
//                } finally {
//                    cursor?.close()
//                }
//                val id = DocumentsContract.getDocumentId(uri)
//                if (id.startsWith("raw:")) {
//                    return id.replaceFirst("raw:".toRegex(), "")
//                }
//                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads"), java.lang.Long.valueOf(id))
//                return getDataColumn(context, contentUri, null, null)
                Cursor cursor = null;
                try {
                    String[] s={MediaStore.MediaColumns.DISPLAY_NAME};
                    cursor = context.getContentResolver().query(uri,s,null,null,null);
                    String fileName = cursor.getString(0);
                    String path = Environment.getExternalStorageDirectory().toString()+"/Download/"+fileName;
                    if (!TextUtils.isEmpty(path)){
                        return path;
                    }
                }finally {
                    cursor.close();
                }

                String id = DocumentsContract.getDocumentId(uri);
                if (id.startsWith("raw:")) {
                    return id.replaceFirst(Pattern.quote("raw:"), "");
                }
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);



            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
