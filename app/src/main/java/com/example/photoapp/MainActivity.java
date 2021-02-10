package com.example.photoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    boolean debug = true;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int SEARCH_ACTIVITY_REQUEST_CODE = 10;
    String mCurrentPhotoPath;
    public ArrayList<String> photos = null;
    private int index = 0;
    private FusedLocationProviderClient fusedLocationClient;
    public String latloc;
    public String longloc;

    //String apiKey = BuildConfig.API_KEY;

    public void showText(String text) {
        if (debug) Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);


        photos = findPhotos(new Date(Long.MIN_VALUE), new Date(), "");

        if (photos.size() == 0) {
            displayPhoto(null);
        } else {
            displayPhoto(photos.get(index));
        }

        View.OnClickListener sharehandler = new View.OnClickListener() {
            public void onClick(View v) {
                shareImage();
            }
        };
        findViewById(R.id.shareid).setOnClickListener(sharehandler);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latloc = Location.convert(location.getLatitude(), Location.FORMAT_DEGREES);
                            longloc = Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);

                        }
                        else{
                            latloc = "0-00000";
                            longloc = "0-00000";
                            showText("There is no location recorded. Please open a location documenting app");
                        }
                    }
                });
    }

    public void click_snap(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null; //raymond thinks white photo is appearing here
            try {

                photoFile = createImageFile();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.photoapp.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }
    }

    public void click_prev(View v) {
        updatePhoto(photos.get(index), ((EditText) findViewById(R.id.captionid)).getText().toString());
        if (index < (photos.size() - 1)) {
            index++;
        }
        displayPhoto(photos.get(index));
    }

    public void click_next(View v) {
        updatePhoto(photos.get(index), ((EditText) findViewById(R.id.captionid)).getText().toString());
        if (index > 0) {
            index--;
        }
        displayPhoto(photos.get(index));
    }

    public void filter(View v) {
        Intent i = new Intent(MainActivity.this, SearchActivity.class);
        startActivityForResult(i, SEARCH_ACTIVITY_REQUEST_CODE);
    }


    private ArrayList<String> findPhotos(Date startTimestamp, Date endTimestamp, String keywords) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.photoapp/files/Pictures");
        ArrayList<String> photos = new ArrayList<String>();
        File[] fList = file.listFiles();
        if (fList != null) {
            for (File f : fList) {
                if (((startTimestamp == null && endTimestamp == null) || (f.lastModified() >= startTimestamp.getTime() && f.lastModified() <= endTimestamp.getTime())) && (keywords == null || f.getPath().contains(keywords)))
                    photos.add(f.getPath());
            }
        }
        return photos;
    }

    private void displayPhoto(String path) {
        ImageView iv = (ImageView) findViewById(R.id.thumbnailid);
        TextView tv = (TextView) findViewById(R.id.datetimeid);
        EditText et = (EditText) findViewById(R.id.captionid);
        if (path == null || path == "") {
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

    private File createImageFile() throws IOException {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + latloc + "_" + longloc + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        showText(image.getAbsolutePath());
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DateFormat format = new SimpleDateFormat("yyyy‐MM‐dd HH:mm:ss");
                Date startTimestamp, endTimestamp;
                try {
                    String from = (String) data.getStringExtra("STARTTIMESTAMP");
                    String to = (String) data.getStringExtra("ENDTIMESTAMP");
                    startTimestamp = format.parse(from);
                    endTimestamp = format.parse(to);
                } catch (Exception ex) {
                    startTimestamp = null;
                    endTimestamp = null;
                }
                String keywords = (String) data.getStringExtra("KEYWORDS");
                index = 0;
                photos = findPhotos(startTimestamp, endTimestamp, keywords);
                if (photos.size() == 0) {
                    displayPhoto(null);
                } else {
                    displayPhoto(photos.get(index));
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView mImageView = (ImageView) findViewById(R.id.thumbnailid);
            mImageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }
    }

    private void updatePhoto(String path, String caption) {
        String[] attr = path.split("_");
        if (attr.length >= 3) {
            File to = new File(attr[0] + "_" + caption + "_" + attr[2] + "_" + attr[3]);
            File from = new File(path);
            from.renameTo(to);
        }
        photos = findPhotos(new Date(Long.MIN_VALUE), new Date(), "");
    }

    private void shareImage() {
        Intent share = new Intent(Intent.ACTION_SEND);

        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_TEXT, ((EditText) findViewById(R.id.captionid)).getText().toString());
        share.putExtra(Intent.EXTRA_SUBJECT, "" + ((EditText) findViewById(R.id.captionid)).getText().toString());
        Uri fileUri = FileProvider.getUriForFile(this, "com.example.photoapp.fileprovider", new File(photos.get(index)));
        share.putExtra(Intent.EXTRA_STREAM, fileUri);
        startActivity(Intent.createChooser(share, "Share to"));
    }
}