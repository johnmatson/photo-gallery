package com.example.photoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import androidx.core.content.FileProvider;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    android.widget.Button filter;
    android.widget.Button snap;
    android.widget.Button next;
    android.widget.Button prev;
    ImageView imageView;

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    public ArrayList<String> photos = null;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        filter = (android.widget.Button) findViewById(R.id.filterid);
        snap = (android.widget.Button) findViewById(R.id.snapid);
        imageView = (ImageView) findViewById(R.id.thumbnailid);
        next = (android.widget.Button) findViewById(R.id.nextid);
        prev = (android.widget.Button) findViewById(R.id.previd);

        photos = findPhotos();

        if (photos.size() == 0) {
            displayPhoto(null);
        } else {
            displayPhoto(photos.get(index));
        }

        snap.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                            File photoFile = null;
                                            try {
                                                photoFile = createImageFile();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            if (photoFile != null) {
                                                Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                                                        "com.example.photoapp.fileprovider",
                                                        photoFile);
                                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                            }
                                        }
                                    }
                                //}
        );

        next.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        updatePhoto(photos.get(index), ((EditText) findViewById(R.id.captionid)).getText().toString());
                                        if (index < (photos.size() - 1)) {
                                            index++;
                                        }
                                        displayPhoto(photos.get(index));
                                    }
                                }
        );

        prev.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        updatePhoto(photos.get(index), ((EditText) findViewById(R.id.captionid)).getText().toString());
                                        if (index > 0) {
                                            index--;
                                        }
                                        displayPhoto(photos.get(index));
                                    }
                                }
        );
    }
    

    private ArrayList<String> findPhotos() {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.photoapp/files/Pictures");
        ArrayList<String> photos = new ArrayList<String>();
        File[] fList = file.listFiles();
        if (fList != null) {
            for (File f : fList) {
                photos.add(f.getPath());
            }
        }
        return photos;
    }

    private void displayPhoto(String path) {
        ImageView iv = (ImageView) findViewById(R.id.thumbnailid);
        TextView tv = (TextView) findViewById(R.id.datetimeid);
        EditText et = (EditText) findViewById(R.id.captionid);
        if (path == null || path =="") {
            iv.setImageResource(R.mipmap.ic_launcher);
            et.setText("");
            tv.setText("");
        } else {
            iv.setImageBitmap(BitmapFactory.decodeFile(path));
            String[] attr = path.split("_");
            et.setText(attr[1]);
            tv.setText(attr[2]);
        }
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
            ImageView mImageView = (ImageView) findViewById(R.id.thumbnailid);
            imageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
            photos = findPhotos();
        }
    }

    private void updatePhoto(String path, String caption) {
        String[] attr = path.split("_");
        if (attr.length >= 3) {
            File to = new File(attr[0] + "_" + caption + "_" + attr[2] + "_" + attr[3]);
            File from = new File(path);
            from.renameTo(to);
        }
    }


}