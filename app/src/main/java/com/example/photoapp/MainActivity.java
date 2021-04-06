package com.example.photoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private Date startTimestamp;
    private Date endTimestamp;
    private String keywords;
    private float latmin;
    private float latmax;
    private float longmin;
    private float longmax;
    private boolean filtered = false;

    public ArrayList<Integer> loclist = new ArrayList<Integer>();

    //String apiKey = BuildConfig.API_KEY;

    public void showText(String text) {
        if (debug) Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    // Create the Main Activity Page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
        startTimestamp = null;
        endTimestamp = null;
        keywords = null;

        photos = findPhotos();


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

    // Take photo Button
    // Opens android camera through API call
    public void click_snap(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = null;
                photoFile = createImageFile();
                if (photoFile != null) { // Continue only if the File was successfully created
                    Uri photoURI = FileProvider.getUriForFile(this, "com.example.photoapp.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Buttons to change through the photos.  If there is no photo after hitting "next" or "prev" do nothing (Keeps app from crashing)
    public void click_prev(View v) {
        if(photos.size()>0) {
            updatePhoto(photos.get(index), ((EditText) findViewById(R.id.captionid)).getText().toString());
            if (index < (photos.size() - 1)) {
                index++;
            }
            displayPhoto(photos.get(index));
        }
    }

    public void click_next(View v) {
        if(photos.size()>0) {
            updatePhoto(photos.get(index), ((EditText) findViewById(R.id.captionid)).getText().toString());
            if (index > 0) {
                index--;
            }
            displayPhoto(photos.get(index));
        }
    }

    public void delete(View v) {
        File image = new File(mCurrentPhotoPath);
        image.delete();
    }
    // Search button. Opens the search activity
    public void filter(View v) {
        Intent i = new Intent(MainActivity.this, SearchActivity.class);
        startActivityForResult(i, SEARCH_ACTIVITY_REQUEST_CODE);
    }

    // Takes the input from the search activity and updates the list of photos that match teh criteria.
    private ArrayList<String> findPhotos() {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.photoapp/files/Pictures");
        ArrayList<String> photos = new ArrayList<String>();
        File[] fList = file.listFiles();
        if (fList != null) {
            for (File f : fList) {
                if(filtered) {
                    String[] splitname = f.getPath().split("/");
                    String[] splitname2 = splitname[splitname.length - 1].split("_");

                    float flat = Float.parseFloat(splitname2[3]);
                    float flong = Float.parseFloat(splitname2[4]);
                    float aaa = (float) 0.0;

                    // if (((startTimestamp == null && endTimestamp == null) || (f.lastModified() >= startTimestamp.getTime() &&
                    //        f.lastModified() <= endTimestamp.getTime())) && (keywords == null || f.getPath().contains(keywords)) &&
                    //        (flat > latmin && flat < latmax && flong > longmin && flong < longmax))
                    if (flat > latmin && flat < latmax && flong > longmin && flong < longmax)
                        photos.add(f.getPath());
                }
                else{
                    photos.add(f.getPath());
                }
            }
        }
        return photos;
    }


    // Shows photos that are currently in the "list" of photo.
    // List is updated via function above. If no search, list is all photos save by the app.
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

    // Creates the image file, and names it. Naming convention is designed to store timestamp and the Lat/Long Location data.
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

    // Some needs to commment this function, dont know what this does at all....
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DateFormat format = new SimpleDateFormat("yyyy‐MM‐dd HH:mm:ss");
                try {
                    String from = (String) data.getStringExtra("STARTTIMESTAMP");
                    String to = (String) data.getStringExtra("ENDTIMESTAMP");
                    startTimestamp = format.parse(from);
                    endTimestamp = format.parse(to);
                } catch (Exception ex) {
                    startTimestamp = null;
                    endTimestamp = null;
                }

                latmin = data.getFloatExtra("LATMIN", (float)0.0);
                latmax = data.getFloatExtra("LATMAX", (float)0.0);
                longmin = data.getFloatExtra("LONGMIN", (float)0.0);
                longmax = data.getFloatExtra("LONGMAX", (float)0.0);

                keywords = (String) data.getStringExtra("KEYWORDS");
                index = 0;

                filtered = true;
                photos = findPhotos();

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

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_CANCELED){
            File image = new File(mCurrentPhotoPath);
            image.delete();
        }
    }

    // updates the photo caption I think?
    private void updatePhoto(String path, String caption) {
        String[] attr = path.split("_");
        if (attr.length >= 5) {
            File to = new File(attr[0] + "_" + caption + "_" + attr[2] + "_" + attr[3] + "_" + attr[4] + "_" + attr[5]);
            File from = new File(path);
            from.renameTo(to);
        }
        photos = findPhotos();
    }


    // Uses the Share API in android to open share widget and allow us to send via chosen
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