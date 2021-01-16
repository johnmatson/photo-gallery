package com.example.photoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import androidx.core.content.FileProvider;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    android.widget.Button filter;
    android.widget.Button snap;
    ImageView imageView;

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        filter = (android.widget.Button) findViewById(R.id.button4);
        snap = (android.widget.Button) findViewById(R.id.button3);
        imageView = (ImageView) findViewById(R.id.imageView);

        snap.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                               /*File photoFile = null;
                                               try {
                                                   photoFile = createImageFile();
                                               } catch (IOException e) {
                                                   e.printStackTrace();
                                               }
                                               if(photoFile != null) {
                                                   Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                                                           "com.example.android.fileprovider",
                                                           photoFile);
                                                   takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);*/
                                                   startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                               }
                                           }
                                       //}
        );

    }
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap finalPhoto = (Bitmap) extras.get("data");
            imageView.setImageBitmap(finalPhoto);

        }
    }
}